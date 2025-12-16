module org.example.hibernate {
    requires javafx.controls;
    requires javafx.fxml;


    requires static lombok;

    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires java.naming;
    requires mysql.connector.j;


    // Necesario para JavaFX (FXML)
    opens org.example.hibernate to javafx.fxml;
    opens org.example.hibernate.controllers to javafx.fxml;

    exports org.example.hibernate;
    opens org.example.hibernate.services to org.hibernate.orm.core;
    opens org.example.hibernate.utils to org.hibernate.orm.core;
    opens org.example.hibernate.copia to org.hibernate.orm.core;
    opens org.example.hibernate.pelicula to org.hibernate.orm.core;
    opens org.example.hibernate.usuario to org.hibernate.orm.core;

}
