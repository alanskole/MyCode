package model;
//has to implement comparable in order to use parkingspot as the key for the TreeMap containing the schedule for eah spot
public class Parkingspot implements Comparable<Parkingspot>{
    private int spotid;
    private TYPE type;

    public Parkingspot() {}

    public Parkingspot(int spotid, TYPE type) {
        this.spotid = spotid;
        this.type = type;
    }

    public int getSpotid() {
        return spotid;
    }

    public void setSpotid(int id) throws Exception {
        this.spotid = id;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("Spot-ID: " + this.getSpotid() + ", Type: " + this.getType());
    }

    //has to implemented because the interface comparable is implemented
    //this is so the TreeMap can use the parkingspot-Ids to differentiate
    //between the different spots inside the TreeMap, usually keys of maps are
    //Strings and Integers because they can be compared
    @Override
    public int compareTo(Parkingspot other) {
        if (other == null) {
            return 1;
        }
        return this.getSpotid() - other.getSpotid();
    }
}
