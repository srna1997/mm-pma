package ba.sum.fpmoz.dnevnik.model;

public class Curses {
   public String uId;
   public String name;

   public Curses() {
   }

   public Curses(String uId, String name) {
      this.uId = uId;
      this.name = name;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
