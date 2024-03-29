package Classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author giang
 */
public class BrandList {
    //tao mot bien thuoc lop BrandList kieu ArrayList trong do 
    //cac phan tu la obj thuoc lop Brand
    ArrayList<Brand> bList;
    //tao constructor default
    public BrandList() {
        bList = new ArrayList<>();
    }
    //lay du lieu tu file, tao cac obj brand,roi truyen vao bList
    public boolean loadFromFile(String fileName) {
        File f = new File(fileName);                    //tao 1 obj f de tro toi file can doc 
        if (!f.exists()) {                  //kiem tra xem file co ton tai khong, neu khong ton tai tra ve false, neu ton tai nhay sang else
            return false;
        } else {
            FileReader fr = null;                   //tao 1 bien de doc tung ki tu trong file
            BufferedReader r = null;                    //tao 1 bien de doc 1 chuoi ki tu trong file
            try {
                fr = new FileReader(fileName);                  //tao 1 obj de doc tung ki tu trong file 
                r = new BufferedReader(fr);                 //tao 1 obj de doc 1 chuoi ki tu trong file
                while (r.ready()) {                 //kiem tra xem r da toi eof chua   
                    String s = r.readLine();                    //doc 1 chuoi cho den khi gap /n va chuyen chuoi do vao s
                    String[] arr = s.split(",|:");                  //tach chuoi do ra khi gap dau ";" or dau": va sau do chuyen vao mang arr
                    if (arr.length == 4) {                      //kiem tra mang xem co du 4 phan tu khong neu co thuc hien block lenh 
                        Brand b = new Brand(arr[0], arr[1], arr[2], Double.parseDouble(arr[3].trim()));                 //tao 1 obj thuoc Brand roi chuyen gia tri cua mang vao lam tham so
                        bList.add(b);                   //them obj moi tao vao mang bList
                    }
                }
            } catch (IOException e) {
            } finally {
                try {
                    if (r != null) {                    //kiem tra xem bien r co null ko, neu ko null dong lien ket cua bien r voi file
                        r.close();
                    }
                    if (fr != null) {                   //kiem tra xem bien fr co null ko, neu ko null dong lien ket cua bien frvoi file
                        fr.close();
                    }
                } catch (IOException e) {
                }
            }
            return true;
        }
    }
    //ghi du lieu tu bList vao file
    public boolean saveToFile(String fileName){
        File f = new File(fileName);                    //tao 1 obj roi noi obj do toi file
        try {
            FileWriter fw = new FileWriter(f);                  //tao 1 obj roi noi bien do toi file
            for (Brand b : bList) {                 //cho duyet het cac phan tu trong mang
                fw.write(b.toString() + "\n");
            }
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
    //tim index dua tren brandID
    public int searchID(String bID) {
        for (int i = 0; i < bList.size(); i++) {
            if (bList.get(i).getBrandID().equalsIgnoreCase(bID)) {
                return i;
            }
        }
        return -1;
    }
    //lay userChoice banh cach dua brandlist ra menu
    public Brand getUserChoice() {
        ArrayList<Brand> options = new ArrayList<>();
        for (Brand b : bList) {
            options.add(b);
        }
        Menu mnu = new Menu();
        return (Brand) mnu.ref_getChoice(options);
    }
    //them brand vao bList
    public void addBrand() {
        Scanner sc = new Scanner(System.in);
        String id, name, sound;
        double price;
        //nhap brandID
        while (true) {
            System.out.print("Enter brand ID: ");
            id = sc.nextLine();
            // Check if ID already in brandList
            // If not exist, accept ID and break the loop
            if (searchID(id) == -1) {
                break;
            }
            System.out.println("Please re-enter. Cannot add existing ID");
        }
        //nhap brandName
        while (true) {
            System.out.print("Enter brand name: ");
            name = sc.nextLine();
            // Check if name is blank
            if (!name.isEmpty()) {
                break;
            }
            System.out.println("Please re-enter. Brand name cannot be blank");
        }
        //nhap soundBrand
        while (true) {
            System.out.print("Enter sound brand: ");
            sound = sc.nextLine();
            if (!sound.isEmpty()) {
                break;
            }
            System.out.println("Please re-enter. Sound brand cannot be blank");
        }
        //nhap price
        while (true) {
            System.out.print("Enter price: ");
            // try-catch to handle input String case
            try {
                price = Double.parseDouble(sc.nextLine());
                if (price > 0) {
                    break;
                }
                throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                System.out.println("Please re-enter. Price must be positive real number");
            }
        }
        //them obj ms tao vao bList
        bList.add(new Brand(id, name, sound, price));
    }
    //thay doi thong tin cua 1 brand bang brandID
    public void updateBrand() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter brand ID to update: ");
        String id = sc.nextLine();
        //tim index cua brand dua vao brandID
        int pos = searchID(id);
        if (pos == -1) {
            System.out.println("Not found!");
            return;
        }
        //thay doi thong tin cua brand 
        String name, sound;
        double price;
        //thay doi brandName
        while (true) {
            System.out.print("Enter brand name: ");
            name = sc.nextLine();
            // Check if name is blank
            if (!name.isEmpty()) {
                break;
            }
            System.out.println("Please re-enter. Brand name cannot be blank");
        }
        //thay doi soundBrand
        while (true) {
            System.out.print("Enter sound brand: ");
            sound = sc.nextLine();
            if (!sound.isEmpty()) {
                break;
            }
            System.out.println("Please re-enter. Sound brand cannot be blank");
        }
        //thay doi price
        while (true) {
            System.out.print("Enter price: ");
            // try-catch to handle input String case
            try {
                price = Double.parseDouble(sc.nextLine());
                if (price > 0) {
                    break;
                }
                throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                System.out.println("Please re-enter. Price must be positive real number");
            }
        }
        //cap nhat lai brandID voi thong tin moi
        bList.set(pos, new Brand(id, name, sound, price));
    }
    //in cac brand ra man hinh
    public void listBrands() {
        for (Brand b : bList) {
            System.out.println(b);
        }
    }
}
