package sprites;

public class Sensor {
    public Entity entity;
    public String name;
    public String sensorId;

    public Sensor(Entity entity,String name, String sensorId) {
        this.entity = entity;
        this.sensorId = sensorId;
        this.name = name;
    }
}
