package com.example.haulmont.backend.entities;

public class Main {
    public static void main(String[] args) {
        PaymentSchedule payment_schedule = new PaymentSchedule();


        int[][] schedule ;

        payment_schedule.AnnuityPayment(50000,22,12);
        schedule = payment_schedule.Schedule(12);

        for (int i = 0; i < 12; i++) {
            System.out.println("Гашение процентов:"+schedule[0][i]+" Гашение тела кредита"+schedule[1][i]);
        }

        double test = 12;
        if(test % 2 != 1 && test % 2 != 0){
            System.out.println("not int");
        }else{
            System.out.println("INT");
        }
    }
}
