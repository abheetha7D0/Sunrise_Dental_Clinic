/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

/**
 *
 * @author ASUS
 */
public interface DentistDAO {

    Dentiest addDentiest(Dentiest patient);

    Dentiest updateDentiest(Dentiest patient);
    
    Dentiest deleteDentiest(Dentiest patient);

    Dentiest findByDentiestName(String name);
}
