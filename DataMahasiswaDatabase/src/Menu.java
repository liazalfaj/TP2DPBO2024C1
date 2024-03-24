import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class Menu extends JFrame{
    public static void main(String[] args) {
        // buat object window
        Menu window = new Menu();

        // atur ukuran window
        window.setSize(480, 560);
        // letakkan window di tengah layar
        window.setLocationRelativeTo(null);
        // isi window
        window.setContentPane(window.mainPanel);
        // ubah warna background
        window.getContentPane().setBackground(Color.white);
        // tampilkan window
        window.setVisible(true);
        // agar program ikut berhenti saat window diclose
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // index baris yang diklik
    private int selectedIndex = -1;
    // list untuk menampung semua mahasiswa
    private ArrayList<Mahasiswa> listMahasiswa;

    private Database database;
    private JPanel mainPanel;
    private JTextField nimField;
    private JTextField namaField;
    private JTable mahasiswaTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox jenisKelaminComboBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel nimLabel;
    private JLabel namaLabel;
    private JLabel jenisKelaminLabel;
    private JLabel StatusMahasiswaLabel;
    private JComboBox statusMahasiswaComboBox;

    // constructor
    public Menu() {
        // inisialisasi listMahasiswa
        listMahasiswa = new ArrayList<>();

        database = new Database();

        // isi listMahasiswa
        populateList();

        // isi tabel mahasiswa
        mahasiswaTable.setModel(setTable());

        // ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // atur isi combo box jenis kelamin
        String[] jenisKelaminData = {"", "Laki-laki", "Perempuan"};
        jenisKelaminComboBox.setModel(new DefaultComboBoxModel(jenisKelaminData));

        //atur isi combo box status mahasiswa
        String[] statusMahasiswaData = {"", "Aktif", "Cuti", "Lulus", "Drop-Out"};
        statusMahasiswaComboBox.setModel(new DefaultComboBoxModel(statusMahasiswaData));

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });
        // saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogResult = JOptionPane.showConfirmDialog(null, "Hapus Data?", "Hapus Data", JOptionPane.YES_NO_OPTION);
                if(dialogResult == JOptionPane.YES_OPTION){
                    deleteData();
                }else{
                    clearForm();
                }

            }
        });
        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // saat tombol
                clearForm();
            }
        });
        // saat salah satu baris tabel ditekan
        mahasiswaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // ubah selectedIndex menjadi baris tabel yang diklik
                selectedIndex = mahasiswaTable.getSelectedRow();

                // simpan value textfield dan combo box
                String selectedNim = mahasiswaTable.getModel().getValueAt(selectedIndex, 1).toString();
                String selectedNama = mahasiswaTable.getModel().getValueAt(selectedIndex, 2).toString();
                String selectedJenisKelamin = mahasiswaTable.getModel().getValueAt(selectedIndex, 3).toString();
                String selectedStatusMahasiswa = mahasiswaTable.getModel().getValueAt(selectedIndex, 4).toString();

                // ubah isi textfield dan combo box
                nimField.setText(selectedNim);
                namaField.setText(selectedNama);
                jenisKelaminComboBox.setSelectedItem(selectedJenisKelamin);
                statusMahasiswaComboBox.setSelectedItem(selectedStatusMahasiswa);

                // ubah button "Add" menjadi "Update"
                addUpdateButton.setText("Update");
                // tampilkan button delete
                deleteButton.setVisible(true);
            }
        });
    }

    public final DefaultTableModel setTable() {
        // tentukan kolom tabel
        Object[] column = {"No", "NIM", "Nama", "Jenis Kelamin", "Status Mahasiswa"};

        // buat objek tabel dengan kolom yang sudah dibuat
        DefaultTableModel temp = new DefaultTableModel(null, column);
        try{
            ResultSet resultSet = database.selectQuery("SELECT * FROM mahasiswa");

            // isi tabel dengan listMahasiswa
            int i = 0;
            while(resultSet.next()) {
                Object[] row = new Object[5];
                row[0] = i + 1;
                row[1] = resultSet.getString("nim");
                row[2] = resultSet.getString("nama");
                row[3] = resultSet.getString("jenis_kelamin");
                row[4] = resultSet.getString("status_mahasiswa");

                temp.addRow(row);
                i++;
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }


        return temp;
    }

    public void insertData() {
        int selectedIndex = mahasiswaTable.getSelectedRow();

        // ambil data dari form
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        String statusMahasiswa = statusMahasiswaComboBox.getSelectedItem().toString();

        if(Objects.equals(nimField.getText(), "") || Objects.equals(namaField.getText(), "") || jenisKelaminComboBox.getSelectedItem() == "" || statusMahasiswaComboBox.getSelectedItem() == ""){
            JOptionPane.showMessageDialog(new JOptionPane(), "Form tidak boleh ada yang kosong!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            boolean nimExists = false;
            try {
                ResultSet resultSet = database.selectQuery("SELECT nim FROM mahasiswa");

                // Check if the nim already exists in the database
                while (resultSet.next()) {
                    String CNim = resultSet.getString("nim");
                    if (Objects.equals(nim, CNim)) {
                        nimExists = true;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (nimExists == true) {
                JOptionPane.showMessageDialog(null, "Data NIM sudah terdaftar!", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                String sql = "INSERT INTO mahasiswa VALUES (null, '" + nim + "', '" + nama + "', '" + jenisKelamin + "', '" + statusMahasiswa + "');";
                database.insertUpdateDeleteQuery(sql);

                // update tabel
                mahasiswaTable.setModel(setTable());

                // bersihkan form
                clearForm();

                // feedback
                System.out.println("Insert berhasil!");
                JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
            }
        }
    }

    public void updateData() {
        int selectedIndex = mahasiswaTable.getSelectedRow();

        // Get the current nim of the selected row
        String currentNim = mahasiswaTable.getValueAt(selectedIndex, 1).toString();

        // ambil data dari form
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        String statusMahasiswa = statusMahasiswaComboBox.getSelectedItem().toString();

        if(Objects.equals(nimField.getText(), "") || Objects.equals(namaField.getText(), "") || jenisKelaminComboBox.getSelectedItem() == "" || statusMahasiswaComboBox.getSelectedItem() == ""){
            JOptionPane.showMessageDialog(null, "Form tidak boleh ada yang kosong!", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            boolean nimExists = false;
            try {
                ResultSet resultSet = database.selectQuery("SELECT nim FROM mahasiswa");

                // Check if the nim already exists in the database
                while (resultSet.next()) {
                    String CNim = resultSet.getString("nim");
                    if (Objects.equals(nim, CNim)) {
                        nimExists = true;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if(nimExists == true){
                JOptionPane.showMessageDialog(null, "Data NIM sudah terdaftar!", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                // ubah data mahasiswa ke dalam database
                String sql = "UPDATE mahasiswa SET nim = '" + nim + "', nama = '" + nama + "', jenis_kelamin = '" + jenisKelamin + "', status_mahasiswa = '" + statusMahasiswa + "' WHERE nim = '" + currentNim + "'";
                database.insertUpdateDeleteQuery(sql);

                // update tabel
                mahasiswaTable.setModel(setTable());

                // bersihkan form
                clearForm();

                // feedback
                System.out.println("Update Berhasil!");
                JOptionPane.showMessageDialog(null, "Data berhasil diubah!");
            }
        }
    }



    public void deleteData() {
        int selectedIndex = mahasiswaTable.getSelectedRow();

        // Get the current nim of the selected row
        String currentNim = mahasiswaTable.getValueAt(selectedIndex, 1).toString();

            // Construct the SQL DELETE statement
            String sql = "DELETE FROM mahasiswa WHERE nim = '" + currentNim + "'";

            // Execute the DELETE query
            database.insertUpdateDeleteQuery(sql);

            // Update the table view
            mahasiswaTable.setModel(setTable());

            // Clear the form
            clearForm();

            // Provide feedback
            System.out.println("Delete berhasil!");
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
    }

    public void clearForm() {
        // kosongkan semua texfield dan combo box
        nimField.setText("");
        namaField.setText("");
        jenisKelaminComboBox.setSelectedItem("");
        statusMahasiswaComboBox.setSelectedItem("");

        // ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");
        // sembunyikan button delete
        deleteButton.setVisible(false);
        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
    }

    private void populateList() {
        listMahasiswa.add(new Mahasiswa("2203999", "Amelia Zalfa Julianti", "Perempuan", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2202292", "Muhammad Iqbal Fadhilah", "Laki-laki", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2202346", "Muhammad Rifky Afandi", "Laki-laki", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2210239", "Muhammad Hanif Abdillah", "Laki-laki", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2202046", "Nurainun", "Perempuan", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2205101", "Kelvin Julian Putra", "Laki-laki", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2200163", "Rifanny Lysara Annastasya", "Perempuan", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2202869", "Revana Faliha Salma", "Perempuan", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2209489", "Rakha Dhifiargo Hariadi", "Laki-laki","Aktif"));
        listMahasiswa.add(new Mahasiswa("2203142", "Roshan Syalwan Nurilham", "Laki-laki", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2200311", "Raden Rahman Ismail", "Laki-laki", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2200978", "Ratu Syahirah Khairunnisa", "Perempuan", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2204509", "Muhammad Fahreza Fauzan", "Laki-laki", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2205027", "Muhammad Rizki Revandi", "Laki-laki", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2203484", "Arya Aydin Margono", "Laki-laki", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2200481", "Marvel Ravindra Dioputra", "Laki-laki", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2209889", "Muhammad Fadlul Hafiizh", "Laki-laki", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2206697", "Rifa Sania", "Perempuan", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2207260", "Imam Chalish Rafidhul Haque", "Laki-laki", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2204343", "Meiva Labibah Putri", "Perempuan", "Aktif"));
    }
}
