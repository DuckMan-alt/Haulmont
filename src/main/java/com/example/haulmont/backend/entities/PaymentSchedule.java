package com.example.haulmont.backend.entities;

public class PaymentSchedule {

    private int paymentAmount;
    private int remains;
    private double month_percent;

    public void AnnuityPayment(int credit_sum,
                             double percent,
                             int time_interval){

        month_percent = (percent/100)/12;
        paymentAmount = (int) Math.ceil(credit_sum *
                                (month_percent +
                                (month_percent/
                                (Math.pow((1+month_percent),time_interval)-1))));
        remains = credit_sum;

    }
    private int Sum_interest_repayment(){
        int repayment = (int) Math.ceil(remains *month_percent);
        return repayment;
    }

    private int LoanBody(int repayment){
        int loanBody = paymentAmount - repayment;
        return loanBody;
    }

    public int[][] Schedule(int time_interval){
        int[][] schedule = new int[2][time_interval];
        for (int i = 0; i < time_interval; i++) {
            schedule[0][i] = Sum_interest_repayment();
            schedule[1][i] = paymentAmount - schedule[0][i];
            remains = remains -schedule[1][i];
        }
        return schedule;
    }

    public int getPaymentAmount() {
        return paymentAmount;
    }

    public int getRemains() {
        return remains;
    }

    public double getMonth_percent() {
        return month_percent;
    }
}
