package edu.depual.csc472.sgao_finalproject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Shawn on 10/30/2015.
 */
public class Car {
    enum Type {Sedan, SUV, Wagon, Hatchback, PickupTruck, Minivan, Convertible, Coupe}

    enum Size {Compact, FullSize, MidSize}

    enum Brand {BMW, Honda, Toyota, VW}

    String name;
    Brand brand;
    Type type;
    Size size;
    int rating = 0;

    public Car(String name, Brand brand, Type type) {
        this.name = name;
        this.type = type;
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String toString() {
        return name;
    }

    public static int getIconResource(Brand brand) {
        switch (brand) {
            case BMW:
                return R.drawable.bmw;
            case Honda:
                return R.drawable.honda;
            case Toyota:
                return R.drawable.toyota;
            case VW:
                return R.drawable.vw;
        }
        return -1;
    }

    public static int getImageResource(String name) {
        switch (name) {
            case "4 Runner":
                return R.drawable.fourrunner;
            case "Avalon":
                return R.drawable.avalon;
            case "Camry":
                return R.drawable.camry;
            case "Corolla":
                return R.drawable.corolla;
            case "Highlander":
                return R.drawable.highlander;
            case "Land Cruiser":
                return R.drawable.landcruiser;
            case "Prius":
                return R.drawable.prius;
            case "Rav4":
                return R.drawable.rav4;
            case "Sequoia":
                return R.drawable.sequoia;
            case "Sienna":
                return R.drawable.sienna;
            case "Tacoma":
                return R.drawable.tacoma;
            case "Tundra":
                return R.drawable.tundra;
            case "Yaris":
                return R.drawable.yaris;
            case "Accord":
                return R.drawable.accord;
            case "Civic":
                return R.drawable.civic;
            case "CR-V":
                return R.drawable.crv;
            case "Odyssey":
                return R.drawable.odyssey;
            case "Pilot":
                return R.drawable.pilot;
            case "BMW X5":
                return R.drawable.x5;
            case "BMW 328":
                return R.drawable.bmw328;
            case "Golf SportWagen":
                return R.drawable.golfwagon;
            case "Beetle":
                return R.drawable.beetle;
            case "CC":
                return R.drawable.cc;
            case "Touareg":
                return R.drawable.touareg;
            case "Z4":
                return R.drawable.z4;
            case "Eos":
                return R.drawable.eos;
        }
        return -1;
    }

    public static final Car[] CARS = {
            new Car("4 Runner", Brand.Toyota, Car.Type.SUV),
            new Car("Avalon", Brand.Toyota, Type.Coupe),
            new Car("Camry", Brand.Toyota, Car.Type.Sedan),
            new Car("Corolla", Brand.Toyota, Car.Type.Sedan),
            new Car("Highlander", Brand.Toyota, Car.Type.SUV),
            new Car("Land Cruiser", Brand.Toyota, Car.Type.SUV),
            new Car("Prius", Brand.Toyota, Car.Type.Hatchback),
            new Car("Rav4", Brand.Toyota, Car.Type.SUV),
            new Car("Sequoia", Brand.Toyota, Car.Type.SUV),
            new Car("Sienna", Brand.Toyota, Car.Type.Minivan),
            new Car("Tacoma", Brand.Toyota, Car.Type.PickupTruck),
            new Car("Tundra", Brand.Toyota, Car.Type.PickupTruck),
            new Car("Yaris", Brand.Toyota, Car.Type.Hatchback),
            new Car("Accord", Brand.Honda, Type.Sedan),
            new Car("Civic", Brand.Honda, Type.Sedan),
            new Car("CR-V", Brand.Honda, Type.SUV),
            new Car("Odyssey", Brand.Honda, Type.Minivan),
            new Car("Pilot", Brand.Honda, Type.SUV),
            new Car("BMW X5", Brand.BMW, Type.SUV),
            new Car("BMW 328", Brand.BMW, Type.Sedan),
            new Car("Golf SportWagen", Brand.VW, Type.Wagon),
            new Car("Beetle", Brand.VW, Type.Hatchback),
            new Car("CC", Brand.VW, Type.Sedan),
            new Car("Touareg", Brand.VW, Type.SUV),
            new Car("Z4", Brand.BMW, Type.Convertible),
            new Car("Eos", Brand.VW, Type.Convertible),
    };

}
