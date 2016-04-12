package edu.depual.csc472.sgao_assignment6;

/**
 * Created by Shawn on 10/30/2015.
 */
public class Car {
    enum Type {Sedan, SUV, Wagon, Hatchback, PickupTruck, Minivan, Convertible, Coupe}

    enum Size {Compact, FullSize, MidSize}

    String name;
    Type type;
    Size size;

    public Car(String name, Type type, Size size) {
        this.name = name;
        this.type = type;
        this.size = size;
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

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public String toString() {
        return name;
    }

    public static int getIconResource(Type type) {
        switch (type) {
            case Sedan:
                return R.drawable.sedan;
            case SUV:
                return R.drawable.suv;
            case Wagon:
                return R.drawable.wagon;
            case Hatchback:
                return R.drawable.hatchback;
            case PickupTruck:
                return R.drawable.pickup;
            case Minivan:
                return R.drawable.minivan;
            case Convertible:
                return R.drawable.convertible;
            case Coupe:
                return R.drawable.coupe;
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
        }
        return -1;
    }
}
