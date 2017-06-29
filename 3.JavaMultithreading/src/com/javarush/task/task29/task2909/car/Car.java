package com.javarush.task.task29.task2909.car;

import java.util.Date;

public abstract class Car {
    static public final int TRUCK = 0;
    static public final int SEDAN = 1;
    static public final int CABRIOLET = 2;

//    double fuel;
//    public double summerFuelConsumption;
//    public double winterFuelConsumption;
//    public double winterWarmingUp;
//    private int type;
//    private boolean driverAvailable;
//    private int numberOfPassengers;
//    protected Car(int type, int numberOfPassengers) {
//        this.type = type;
//        this.numberOfPassengers = numberOfPassengers;
//    }
//    public static Car create(int type, int numberOfPassengers){
//        switch (type){
//            case 0:
//                return new Truck(numberOfPassengers);
//            case 1:
//                return new Sedan(numberOfPassengers);
//            case 2:
//                return new Cabriolet(numberOfPassengers);
//            default:
//                return null;
//        }
//    }
//    public void fill(double numberOfLiters) throws Exception
//    {
//        if (numberOfLiters < 0)
//            throw new Exception();
//        fuel += numberOfLiters;
//    }
//    boolean isSummer(Date date , Date summerStart, Date summerEnd){
//        return date.after(summerStart) && date.before(summerEnd);
//    }
//    double getWinterConsumption(int length){
//        return length * winterFuelConsumption + winterWarmingUp;
//    }
//    double getSummerConsumption(int length){
//        return length * summerFuelConsumption;
//    }
//    public double getTripConsumption(Date date, int length, Date SummerStart, Date SummerEnd)
//    {
//        double consumption;
//        if (isSummer(date,SummerStart,SummerEnd)) {
//            consumption = getWinterConsumption(length);
//        }
//        else {
//            consumption = getSummerConsumption(length);
//        }
//        return consumption;
//    }
//    public int getNumberOfPassengersCanBeTransferred() {
//        if (canPassengersBeTransferred()){
//            return numberOfPassengers;
//        }
//        return 0;
//    }
//    private boolean canPassengersBeTransferred(){
//        if(isDriverAvailable() && fuel > 0){
//            return true;
//        }
//        else return false;
//    }
//    public boolean isDriverAvailable() {
//        return driverAvailable;
//    }
//    public void setDriverAvailable(boolean driverAvailable) {
//        this.driverAvailable = driverAvailable;
//    }
//    public void startMoving() {
//        if (numberOfPassengers > 0)
//        {
//            fastenPassengersBelts();
//        }
//        fastenDriverBelt();
//    }
//    public void fastenPassengersBelts() {
//    }
//    public void fastenDriverBelt() {
//    }
//    public abstract int getMaxSpeed();
//
//}

    double fuel;

    public double summerFuelConsumption;
    public double winterFuelConsumption;
    public double winterWarmingUp;

    private int type;

    private boolean driverAvailable;
    private int numberOfPassengers;

    protected Car(int type, int numberOfPassengers) {
        this.type = type;
        this.numberOfPassengers = numberOfPassengers;
    }

    public static Car create(int type, int numberOfPassengers) {
        switch (type) {
            case TRUCK: return new Truck(numberOfPassengers);
            case SEDAN: return new Sedan(numberOfPassengers);
            case CABRIOLET: return new Cabriolet(numberOfPassengers);
            default: return null;
        }
    }

    private boolean isSummer(Date date , Date summerStart, Date summerEnd) { // был косяк с модификатором!!
        return date.before(summerEnd) && date.after(summerStart);
    }

    private double getWinterConsumption(int length) {
        return winterFuelConsumption * length + winterWarmingUp;
    }

    private double getSummerConsumption(int length) {
        return summerFuelConsumption*length;
    }

    public void fill(double numberOfLiters) throws Exception{
        if (numberOfLiters < 0)
            throw new Exception();
        fuel += numberOfLiters;
    }

    public double getTripConsumption(Date date, int length, Date SummerStart, Date SummerEnd) {
        if (isSummer(date, SummerStart, SummerEnd)) {
            return getSummerConsumption(length);
        } else {
            return getWinterConsumption(length);
        }
    }

    private boolean canPassengersBeTransferred() {
        return isDriverAvailable() && fuel > 0;
    }

    public int getNumberOfPassengersCanBeTransferred() {
        if (canPassengersBeTransferred())
            return numberOfPassengers;
        else
            return 0;
    }

    public boolean isDriverAvailable() {
        return driverAvailable;
    }

    public void setDriverAvailable(boolean driverAvailable) {
        this.driverAvailable = driverAvailable;
    }

    public void startMoving() {

        if (getNumberOfPassengersCanBeTransferred() > 0) {
            fastenPassengersBelts();
        }
        fastenDriverBelt();
    }

    public void fastenPassengersBelts() {
    }

    public void fastenDriverBelt() {
    }

    abstract int getMaxSpeed();
}