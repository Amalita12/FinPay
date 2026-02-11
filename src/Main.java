import java.util.Scanner;

public  class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Main m = new Main();
        m.startPayment();
    }
    public void startPayment(){
        char tryagain = 'y';
        do{
            menuPrincipal();
            int choice = getChoice("Enter your choice 1 or 2 ");
            controller(choice);
            System.out.println("do you want to continue? y/n");
            tryagain = sc.next().charAt(0);

        }while(tryagain == 'y' || tryagain == 'Y');

    }
    public int getChoice(String message){
        int choice;
        do{
            System.out.println(message);
            choice = sc.nextInt();

        }while(choice > 0 && choice < 9);
        return choice;
    }
    public  void menuPrincipal(){
        System.out.println("=============================================");
        System.out.println("                     Menu");
        System.out.println("=============================================");
        System.out.println("1. Add Payment.");
        System.out.println("2. Display Payment.");
        System.out.println("3. Update Payment.");
        System.out.println("4. manage partial payments.");
        System.out.println("=============================================");
    }
    public void controller(int choice){
        switch(choice){
            case 1 :
                ajouterPayment();
                break;
            case 2 :
                displayPayment();
                break;
            case 3 :
                updatePayment();
                break;
            case 4 :
                managePartialPayments();
                break;
        }

    }
    public void ajouterPayment(){
        System.out.println("=============================================");
        System.out.println("                     Ajouter Payment");
        System.out.println("=============================================");
        System.out.println("Enter date");
        String date = sc.nextLine();
        System.out.println("enter paymenttype");
        String type =  sc.nextLine();

        PaymentDAO.AjouterPayment();
    }
    public void displayPayment(){
        System.out.println("=============================================");
        System.out.println("                     Dispaly Payment");
        System.out.println("=============================================");
        System.out.println("Enter date");
        String date = sc.nextLine();
        System.out.println("enter paymenttype");
        String type =  sc.nextLine();

        PaymentDAO.displayPayment();
    }

    public void updatePayment(){
        System.out.println("=============================================");
        System.out.println("                     Update Payment");
        System.out.println("=============================================");


        PaymentDAO.updatePayment();
    }
    public void managePartialPayments(){
        System.out.println("=============================================");
        System.out.println("                     Remove Payment");
        System.out.println("=============================================");


        PaymentDAO.managePartialPayments();
    }





}