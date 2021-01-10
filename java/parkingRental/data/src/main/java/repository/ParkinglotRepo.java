package repository;
import irepository.InterfaceCrud;
import model.*;
import java.util.concurrent.ConcurrentSkipListMap;

public class ParkinglotRepo implements InterfaceCrud<Parkinglot>{
    private ConcurrentSkipListMap<Integer, Parkinglot> parkinglots = new ConcurrentSkipListMap<>();

    public ConcurrentSkipListMap<Integer, Parkinglot> getParkinglots() {
        return parkinglots;
    }


    public ParkinglotRepo() {

    }

    @Override
    public void create(Parkinglot parkinglot) {
        this.getParkinglots().put(parkinglot.getId(), parkinglot);
    }

    @Override
    public Parkinglot getById(int parkinglotid) {
        return this.getParkinglots().get(parkinglotid);
    }

    @Override
    public ConcurrentSkipListMap<Integer, Parkinglot> getAll() {
        return parkinglots;
    }

    @Override
    public void delete(int parkinglotid) {
        this.getParkinglots().remove(parkinglotid);
    }
}
