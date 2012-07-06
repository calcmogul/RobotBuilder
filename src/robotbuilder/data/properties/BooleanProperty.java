/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robotbuilder.data.properties;

import robotbuilder.data.RobotComponent;

/**
 *
 * @author Alex Henning
 */
public class BooleanProperty extends Property {
    protected Boolean value;
    
    public BooleanProperty() {}
    
    public BooleanProperty(String name, Object defaultValue, String[] validators, RobotComponent component, Boolean value) {
        super(name, defaultValue, validators, component);
        this.value = value;
    }

    @Override
    public Property copy() {
        return new BooleanProperty(name, defaultValue, validators, component, value);
    }

    @Override
    public Object getValue() {
        return (value != null) ? value : defaultValue;
    }
    
    @Override
    public Object getDisplayValue() {
        return ((Boolean) getValue()).booleanValue();
    }

    @Override
    public void setValue(Object value) {
        super.setValue(value);
        this.value = ((Boolean) value);
    }
}
