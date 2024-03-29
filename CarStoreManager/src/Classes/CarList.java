/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author giang
 */
public class CarList {

    ArrayList<Car> cList;
    BrandList brandList;

    public CarList(BrandList bList) {
        cList = new ArrayList<>();
        brandList = bList;
    }

    public boolean loadFromFile(String fileName) {
        File f = new File(fileName);
        if (!f.exists()) {
            return false;
        } else {
            FileReader fr = null;
            BufferedReader r = null;
            try {
                fr = new FileReader(fileName);
                r = new BufferedReader(fr);
                while (true) {
                    String s = r.readLine();
                    if (s == null) {
                        break;
                    }
                    String[] arr = s.split(",");        // ["C01", "B7-2018", "red", "F12345", "E12345"] 
                    int pos = brandList.searchID(arr[1].trim());
                    Brand b = brandList.bList.get(pos);     // Find Brand from brand ID 
                    cList.add(new Car(arr[0], b, arr[2], arr[3], arr[4]));
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

    public boolean saveToFile(String fileName) throws IOException {
        File f = new File(fileName);                    //tao 1 obj roi noi obj do toi file
        try {
            FileWriter fw = new FileWriter(f);                  //tao 1 obj roi noi bien do toi file
            for (Car b : cList) {                 //cho duyet het cac phan tu trong mang
                fw.write(b.toString() + "\n");
            }
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    public int searchID(String carID) {
        for (int i = 0; i < cList.size(); i++) {
            if (cList.get(i).getCarID().equalsIgnoreCase(carID)) {
                return i;
            }
        }
        return -1;
    }

    public int searchFrame(String fID) {
        for (int i = 0; i < cList.size(); i++) {
            if (cList.get(i).getFrameID().equalsIgnoreCase(fID)) {
                return i;
            }
        }
        return -1;
    }

    public int searchEngine(String eID) {
        for (int i = 0; i < cList.size(); i++) {
            if (cList.get(i).getEngineID().equalsIgnoreCase(eID)) {
                return i;
            }
        }
        return -1;
    }

    public void addCar() {
        Scanner sc = new Scanner(System.in);
        String carID, color, frameID, engineID;
        
        while (true) {
            System.out.print("Enter car ID: ");
            carID = sc.nextLine();
            // Check if ID already in carList
            // If not exist, accept ID and break the loop
            if (searchID(carID) == -1) {
                break;
            }
            System.out.println("Please re-enter. Cannot add existing ID");
        }

        Menu m = new Menu();
        Brand b = (Brand) m.ref_getChoice(brandList.bList);

        while (true) {
            System.out.print("Enter color: ");
            color = sc.nextLine();
            // Check if color is blank
            if (!color.isEmpty()) {
                break;
            }
            System.out.println("Please re-enter. Color cannot be blank");
        }

        while (true) {
            System.out.print("Enter frame ID: ");
            frameID = sc.nextLine();
            // Regex check               Duplicate check
            if (frameID.matches("F[0-9]{5}") && searchFrame(frameID) == -1) {
                break;
            }
            System.out.println("Please re-enter. Frame ID must be in the \"F00000\" format and not be duplicated");
        }

        while (true) {
            System.out.print("Enter engine ID: ");
            engineID = sc.nextLine();
            // Regex check               Duplicate check
            if (engineID.matches("E[0-9]{5}") && searchEngine(engineID) == -1) {
                break;
            }
            System.out.println("Please re-enter. Engine ID must be in the \"E00000\" format and not be duplicated");
        }

        cList.add(new Car(carID, b, color, frameID, engineID));
        System.out.println("Add successfully");
    }

    public void printBasedBrandName() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter part of brand name: ");
        String part = sc.nextLine();
        int count = 0;
        for (Car c : cList) {
            if (c.getBrand().getBrandName().contains(part)) {
                System.out.println(c.screenString());
                count++;
            }
        }

        if (count == 0) {
            System.out.println("No car is detected!");
        }
    }

    public boolean removeCar() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter removed ID: ");
        String removedID = sc.nextLine();
        int pos = searchID(removedID);
        if (pos == -1) {
            System.out.println("Not found!");
            return false;
        } else {
            cList.remove(pos);
            System.out.println("Remove successfully");
            return true;
        }
    }

    public boolean updateCar() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter updated ID: ");
        String updatedID = sc.nextLine();
        int pos = searchID(updatedID);
        if (pos == -1) {
            System.out.println("Not found!");
            return false;
        } else {
            Menu m = new Menu();
            Brand b = (Brand) m.ref_getChoice(brandList.bList);
            String color, frameID, engineID;
            while (true) {
                System.out.print("Enter color: ");
                color = sc.nextLine();
                // Check if color is blank
                if (!color.isEmpty()) {
                    break;
                }
                System.out.println("Please re-enter. Color cannot be blank");
            }

            while (true) {
                System.out.print("Enter frame ID: ");
                frameID = sc.nextLine();
                // Regex check               Duplicate check
                if (frameID.matches("F[0-9]{5}") && searchFrame(frameID) == -1) {
                    break;
                }
                System.out.println("Please re-enter. Frame ID must be in the \"F00000\" format and not be duplicated");
            }

            while (true) {
                System.out.print("Enter engine ID: ");
                engineID = sc.nextLine();
                // Regex check               Duplicate check
                if (engineID.matches("E[0-9]{5}") && searchEngine(engineID) == -1) {
                    break;
                }
                System.out.println("Please re-enter. Engine ID must be in the \"E00000\" format and not be duplicated");
            }

            cList.set(pos, new Car(updatedID, b, color, frameID, engineID));
            System.out.println("Update successfully");
            return true;
        }
    }

    public void listCars() {
        Collections.sort(cList);
        for (Car c : cList) {
            System.out.println(c.screenString());
        }
    }

}
