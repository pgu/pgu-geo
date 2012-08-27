package pgu.shared.dto;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Positions implements IsSerializable, ItemsWithLocation {

    private ArrayList<Position> values = new ArrayList<Position>();

    @Override
    public ArrayList<Position> getValues() {
        return values;
    }

    public void setValues(final ArrayList<Position> values) {
        this.values = values;
    }

}
