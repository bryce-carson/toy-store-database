package ca.cyberscientist.toystoredb.view;

import java.util.ArrayList;

public class SearchResults extends View {
    public SearchResults(ArrayList<Toy> toyList) {

        for (Toy toy : toyList) {
            // FIXME: the current toString method is a plain stringification of the object.
            // TODO: the toy's printable/accessible fields need to be semi-colon separated for better string interpolation.
            System.out.println(toy); // Use the toString method.
        }

    }
}