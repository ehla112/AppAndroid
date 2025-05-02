package co.edu.uniminuto.appproject.entities;

public class Citas {
    private int idCita;
    private String fecha;
    private String lugar;
    private String hora;
    private String descripcion;
    private int status;
    private int idMascota;

    public  Citas(){

    }

    public Citas( String fecha, String lugar, String hora,String descripcion, int status, int idMascota) {
        this.fecha = fecha;
        this.lugar = lugar;
        this.hora = hora;
        this.descripcion = descripcion;
        this.status = status;
        this.idMascota = idMascota;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }
    private String nombreMascota;

    public String getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Citas{");
        sb.append("idCita=").append(idCita);
        sb.append(", fecha='").append(fecha).append('\'');
        sb.append(", lugar='").append(lugar).append('\'');
        sb.append(", hora='").append(hora).append('\'');
        sb.append(", descripcion='").append(descripcion).append('\'');
        sb.append(", status=").append(status);
        sb.append(", idMascota=").append(idMascota);
        sb.append('}');
        return sb.toString();
    }
}
