package co.edu.uniminuto.appproject.entities;

public class Vacuna {
    private int id;
    private String nombre;
    private String fecha;
    private String tipo;
    private int idMascota;
    private boolean status;

    public Vacuna() {
    }

    public Vacuna(int id, String nombre, String fecha, String tipo, boolean status) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.tipo = tipo;
        this.status = status;
    }


    public Vacuna(int id, String nombre, String fecha, String tipo, int idMascota, boolean status) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.tipo = tipo;
        this.idMascota = idMascota;
        this.status = status;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getIdMascota() { return idMascota; }
    public boolean getStatus() {return status;}

    public void setIdMascota(int idMascota) { this.idMascota = idMascota; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }


    @Override
    public String toString() {
        return "Vacuna{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fecha='" + fecha + '\'' +
                ", tipo='" + tipo + '\'' +
                ", idMascota=" + idMascota +
                ", status=" + status +
                '}';
    }

}
