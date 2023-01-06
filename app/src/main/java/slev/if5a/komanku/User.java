package slev.if5a.komanku;

public class User {
    private String email;
    private String namaLengkap;

    public User(String email, String namaLengkap) {
        this.email = email;
        this.namaLengkap = namaLengkap;
    }

    public User() {
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }
}
