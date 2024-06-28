package com.aa.act.interview.org;

import java.util.HashSet;
import java.util.Optional;
import java.util.Iterator;
import java.util.Set;

public abstract class Organization {

    private Position root;
    private int identifierNumber = 0;
    
    public Organization() {
        root = createOrganization();
    }
    
    protected abstract Position createOrganization();
    
    /**
     * hire the given person as an employee in the position that has that title
     * 
     * @param person
     * @param title
     * @return the newly filled position or empty if no position has that title
     */
    public Optional<Position> hire(Name person, String title) {
        //your code here
        //Create a new employee
        identifierNumber += 1;
        Employee newHire = new Employee(identifierNumber, person);

        //Add employee with the position that has the given title, starting with root first
        Position newPosition = positionCheck(root, title);
        if(newPosition != null) {
            newPosition.setEmployee(Optional.of(newHire));
            return Optional.of(newPosition);
        }

        return Optional.empty();
    }

    //Added a new method to find and check if position exists
    public Position positionCheck (Position position, String title) {
        Position correctPosition = null;

        if(position.getTitle() == title)
        {
            return position;
        }
        else {
            Iterator<Position> iterator = position.getDirectReports().iterator();
            while(iterator.hasNext())
            {
                Position currentPosition = iterator.next();
                correctPosition = positionCheck(currentPosition, title);
                if(correctPosition != null){
                    return correctPosition;
                }

            }
        }
        return null;
    }

    @Override
    public String toString() {
        return printOrganization(root, "");
    }
    
    private String printOrganization(Position pos, String prefix) {
        StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
        for(Position p : pos.getDirectReports()) {
            sb.append(printOrganization(p, prefix + "  "));
        }
        return sb.toString();
    }
}
