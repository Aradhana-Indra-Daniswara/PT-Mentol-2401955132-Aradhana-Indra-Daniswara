import java.util.ArrayList;
import java.util.Scanner;

public class Main {
  static Scanner scan = new Scanner(System.in);
  ArrayList<Karyawan> karyawan = new ArrayList<Karyawan>();
  int supervisorCount = 0;
  int managerCount = 0;
  int adminCount = 0;

  static void clearScreen() {
    for (int i = 0; i < 50; i++) {
      System.out.println("");
    }
  }

  String generateId() {
    String id;
    char char1 = (char) Math.floor(Math.random() * 26 + 65);
    char char2 = (char) Math.floor(Math.random() * 26 + 65);
    int num1 = (int) Math.floor(Math.random() * 10);
    int num2 = (int) Math.floor(Math.random() * 10);
    int num3 = (int) Math.floor(Math.random() * 10);

    id = String.format("%c%c-%d%d%d", char1, char2, num1, num2, num3);
    return id;
  }

  public Main() {
    menu();
  }

  boolean checkName(String name) {
    if (name.length() >= 3) {
      return true;
    }
    return false;
  }

  boolean checkGender(String gender) {
    if (gender.equals("Laki-laki") || gender.equals("Perempuan")) {
      return true;
    }
    return false;
  }

  boolean checkRole(String role) {
    if (role.equals("Manager") || role.equals("Supervisor") || role.equals("Admin")) {
      return true;
    }
    return false;
  }

  void updateManagerSalary(String target, String currId) {
    System.out.print("Bonus sebesar 10% telah diberikan kepada karyawan dengan id");
    boolean firstTime = true;

    for (Karyawan karyawan : karyawan) {
      if (karyawan.getRole().equals(target) && karyawan.getId() != currId) {
        double currentSalary = karyawan.getSalary();
        switch (target) {
          case "Manager":
            karyawan.setSalary(currentSalary * 110 / 100);
            break;
          case "Supervisor":
            karyawan.setSalary(currentSalary * 107.5 / 100);
            break;
          case "Admin":
            karyawan.setSalary(currentSalary * 105 / 100);
            break;
        }

        if (firstTime) {
          System.out.printf(" " + karyawan.getId());
          firstTime = false;
        } else {
          System.out.printf(", " + karyawan.getId());
        }
      }
    }
    System.out.println("");
  }

  void sortData() {
    int n = karyawan.size();
    for (int i = 0; i < n - 1; i++)
      for (int j = 0; j < n - i - 1; j++)
        if (karyawan.get(j).getName().compareTo(karyawan.get(j + 1).getName()) > 0) {
          // swap
          Karyawan temp = karyawan.get(j);
          karyawan.set(j, karyawan.get(j + 1));
          karyawan.set(j + 1, temp);
        }
  }

  void insertData() {
    clearScreen();
    System.out.println("====================");
    System.out.println("Insert Data Karyawan");
    System.out.println("====================");

    Karyawan newKaryawan = new Karyawan();

    String temp_string;
    // Id karyawan
    newKaryawan.setId(generateId());

    // Nama karyawan
    do {
      System.out.print("Nama Karyawan [ >= 3]: ");
      temp_string = scan.nextLine();
    } while (checkName(temp_string) == false);
    newKaryawan.setName(temp_string);

    // Jenis Kelamin karyawan
    do {
      System.out.print("Jenis Kelamin Karyawan [Laki-laki | Perempuan] (Case Sensitive): ");
      temp_string = scan.nextLine();
    } while (checkGender(temp_string) == false);
    newKaryawan.setGender(temp_string);

    // Role karyawan
    do {
      System.out.print("Role Karyawan [Manager | Supervisor | Admin] (Case Sensitive): ");
      temp_string = scan.nextLine();
    } while (checkRole(temp_string) == false);
    newKaryawan.setRole(temp_string);

    System.out.println("Berhasil menambahkan karyawan dengan id: " + newKaryawan.getId());
    // Salary karyawan
    if (temp_string.equals("Manager")) {
      newKaryawan.setSalary(8000000);

      // Update karyawan sebelumnya
      if (managerCount >= 3) {
        updateManagerSalary("Manager", newKaryawan.getId());
      }
      managerCount++;

    } else if (temp_string.equals("Supervisor")) {
      newKaryawan.setSalary(6000000);

      // Update karyawan sebelumnya
      if (supervisorCount >= 3) {
        updateManagerSalary("Supervisor", newKaryawan.getId());
      }
      supervisorCount++;
    } else {
      newKaryawan.setSalary(4000000);

      // Update karyawan sebelumnya
      if (adminCount >= 3) {
        updateManagerSalary("Admin", newKaryawan.getId());
      }
      adminCount++;
    }

    karyawan.add(newKaryawan);
    sortData();
    System.out.println("ENTER to return");
    scan.nextLine();
    return;
  }

  void displayKaryawan() {
    if (karyawan.isEmpty()) {
      System.out.println("No Data!");
      return;
    }

    System.out.println(
        "|----|-----------------|------------------------------|---------------|--------------|-------------|");
    System.out.println(
        "|No  |Kode Karyawan    |Nama Karyawan                 |Jenis Kelamin  |Jabatan       |Gaji Karyawan|");
    System.out.println(
        "|----|-----------------|------------------------------|---------------|--------------|-------------|");
    int ctr = 1;
    for (Karyawan i : karyawan) {
      System.out.printf("|%4d|%17s|%30s|%15s|%14s|%13d|\n", ctr++, i.getId(),
          i.getName(), i.getGender(), i.getRole(),
          (int) i.getSalary());
    }
    System.out.println(
        "|----|-----------------|------------------------------|---------------|--------------|-------------|");
  }

  void viewData() {
    clearScreen();
    // 4 17 30 15 14 13
    displayKaryawan();
    System.out.println("ENTER to return");
    scan.nextLine();
    return;
  }

  void updateData() {
    clearScreen();
    displayKaryawan();
    if (karyawan.isEmpty()) {
      System.out.println("ENTER to return");
      scan.nextLine();
      return;
    }
    int choice = 0;

    do {
      System.out.println("Pilih no. karyawan yang ingin di UPDATE");
      System.out.printf(">> ");
      choice = scan.nextInt();
      scan.nextLine();

      // karena arraylist indexnya dari 0
      choice--;
    } while (choice >= karyawan.size() || choice < 0); // agar tidak out of bound

    String temp_string;
    // Nama karyawan
    do {
      System.out.print("Nama Karyawan [ >= 3]: ");
      temp_string = scan.nextLine();
    } while (checkName(temp_string) == false);
    karyawan.get(choice).setName(temp_string);

    // Jenis Kelamin karyawan
    do {
      System.out.print("Jenis Kelamin Karyawan [Laki-laki | Perempuan] (Case Sensitive): ");
      temp_string = scan.nextLine();
    } while (checkGender(temp_string) == false);
    karyawan.get(choice).setGender(temp_string);

    // Role karyawan
    do {
      System.out.print("Role Karyawan [Manager | Supervisor | Admin] (Case Sensitive): ");
      temp_string = scan.nextLine();
    } while (checkRole(temp_string) == false);
    karyawan.get(choice).setRole(temp_string);

    System.out.println("Berhasil menambahkan karyawan dengan id: " + karyawan.get(choice).getId());
    // Salary karyawan
    if (temp_string.equals("Manager")) {
      karyawan.get(choice).setSalary(8000000);

      // Update karyawan sebelumnya
      if (managerCount >= 3) {
        updateManagerSalary("Manager", karyawan.get(choice).getId());
      }
      managerCount++;

    } else if (temp_string.equals("Supervisor")) {
      karyawan.get(choice).setSalary(6000000);

      // Update karyawan sebelumnya
      if (supervisorCount >= 3) {
        updateManagerSalary("Supervisor", karyawan.get(choice).getId());
      }
      supervisorCount++;
    } else {
      karyawan.get(choice).setSalary(4000000);

      // Update karyawan sebelumnya
      if (adminCount >= 3) {
        updateManagerSalary("Admin", karyawan.get(choice).getId());
      }
      adminCount++;
    }

    sortData();
    System.out.println("ENTER to return");
    scan.nextLine();
    return;

  }

  void deleteData() {
    clearScreen();
    displayKaryawan();
    if (karyawan.isEmpty()) {
      System.out.println("ENTER to return");
      scan.nextLine();
      return;
    }
    int choice = 0;

    do {
      System.out.println("Pilih no. karyawan yang ingin di DELETE");
      System.out.printf(">> ");
      choice = scan.nextInt();
      scan.nextLine();

      // karena arraylist indexnya dari 0
      choice--;
    } while (choice >= karyawan.size() || choice < 0); // agar tidak out of bound
    karyawan.remove(choice);

    System.out.println("Karyawan deleted successfully!");
    System.out.println("ENTER to return");
    scan.nextLine();
    return;
  }

  void menu() {
    int choice = 0;
    do {
      clearScreen();
      System.out.println("====================");
      System.out.println("     PT Mentol");
      System.out.println("====================");
      System.out.println("Pilih Menu:");
      System.out.println("1. Insert Data Karyawan");
      System.out.println("2. View Data Karyawan");
      System.out.println("3. Update Data Karyawan");
      System.out.println("4. Delete Data Karyawan");
      System.out.println("5. Exit");

      System.out.printf(">> ");
      choice = scan.nextInt();
      scan.nextLine();
      if (choice == 1) {
        insertData();
      } else if (choice == 2) {
        viewData();
      } else if (choice == 3) {
        updateData();
      } else if (choice == 4) {
        deleteData();
      } else if (choice == 5) {
        return;
      }
    } while (choice != 5);

  }

  public static void main(String[] args) {
    new Main();

    scan.close();
  }
}
