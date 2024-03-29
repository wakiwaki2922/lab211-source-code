/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author giang
 */
public class Brand {
    //khai bao field
    private String brandID;
    private String brandName;
    private String soundBrand;
    private double price;
    //tao default constructor
    public Brand() {
    }
    //tao constructor chuyen tham so
    public Brand(String brandID, String brandName, String soundBrand, double price) {
        this.brandID = brandID;
        this.brandName = brandName;
        this.soundBrand = soundBrand;
        this.price = price;
    }
    //getter vs setter
    public String getBrandID() {
        return brandID;
    }

    public void setBrandID(String brandID) {
        this.brandID = brandID;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getSoundBrand() {
        return soundBrand;
    }

    public void setSoundBrand(String soundBrand) {
        this.soundBrand = soundBrand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    //tra ve chuoi theo format "brandID, brandName, SoundBrand: price"
    @Override
    public String toString() {
        return brandID + ", " + brandName + ", " + soundBrand + ": " + price;
    }
    
}
