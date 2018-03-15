package edytafraszczak;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static String[] sheduleMinute;
    public static String[] sheduleHour;
    public static String[] sheduleDayOfMonth;
    public static String[] sheduleDayOfWeek;


    private static String[] dopuszczalneMinuty = {"10", "20", "30", "40", "50", "60", "*"};
    private static String[] dopuszczalneGodziny = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "*"};
    private static String[] dopuszczalneDniMiesiaca = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "*"};
    private static String[] dopuszczalneDniTygpdnia = {"1", "2", "3", "4", "5", "6", "7", "*"};

    public static boolean istnieje(String[] tablica, String wartosc) {
        if (wartosc.contains("-")) {
            return weryfikujWartosci(tablica, wartosc.split("-"));
        }
        for (String wTab : tablica) {
            if (wTab.equalsIgnoreCase(wartosc)) {
                return true;
            }
        }
        return false;
    }

    public static boolean weryfikujWartosci(String[] tablica, String[] wartosci) {
        for (String wartosc : wartosci) {
            if (!istnieje(tablica, wartosc)) {
                return false;
            }
        }
        return true;
    }

    public static String[] konwertujWartosc(String wartosc, String[] dopuszczalneWartosci) {
        String[] wynik = new String[dopuszczalneWartosci.length - 1];
        if (wartosc.equals("*")) {
            wynik = new String[dopuszczalneWartosci.length - 1];
            for (int i = 0; i < dopuszczalneWartosci.length - 1; i++) {
                wynik[i] = dopuszczalneWartosci[i];
            }
        } else {
            int ostatniIndex = 0;
            String[] podzielonePoPrzecinku = wartosc.split(",");
            for (String wartoscPoPrzecinku : podzielonePoPrzecinku) {
                if (wartoscPoPrzecinku.contains("-")) {
                    String[] podzielonePoPrzecinkuIMyslniku = wartoscPoPrzecinku.split("-");
                    int pierwszaWartosc = Integer.parseInt(podzielonePoPrzecinkuIMyslniku[0]);
                    int drugaWartosc = Integer.parseInt(podzielonePoPrzecinkuIMyslniku[1]);
                    for (int i =pierwszaWartosc; i <= drugaWartosc;i++) {
                        wynik[ostatniIndex++] =i+"";
                    }
                } else {
                    wynik[ostatniIndex++] = wartoscPoPrzecinku;
                }
            }

        }

        return wynik;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("cron.txt"));
        String linia = in.nextLine();
        in.close();
        String[] podzielonaLinia = linia.split("\\|");
        if (podzielonaLinia.length != 4) {
            System.out.println("Dane w pliku zle sformatowane, brak 4 pol");
            return;
        }

        //weryfikacja miunt
        String minuty = podzielonaLinia[0];
        //sprawdzenie czy w minuatach nie ma myslnkikow
        if (minuty.contains("-")) {
            System.out.println("Minuty zle sformatowane, zapisano - " + minuty);
            return;
        }
        String[] podzialMinut = minuty.split(",");
        //weryfikacja minut
        if (!weryfikujWartosci(dopuszczalneMinuty, podzialMinut)) {
            System.out.println("Podano bledne minuty " + minuty);
            return;
        }
        //minuty sa ok, zamieniam je na wartosci
        sheduleMinute = konwertujWartosc(minuty, dopuszczalneMinuty);

        String godziny = podzielonaLinia[1];
        String[] podzialGodzn = minuty.split(",");
        //weryfikacja godzin
        if (!weryfikujWartosci(dopuszczalneGodziny, podzialGodzn)) {
            System.out.println("Podano bledne godziny " + godziny);
            return;
        }
        //godziny sa ok, zamieniam je na wartosci
        sheduleHour = konwertujWartosc(godziny, dopuszczalneGodziny);


        String dniMiesiaca = podzielonaLinia[2];
        String[] podzialDniMiesiaca = dniMiesiaca.split(",");
        //weryfikacja dnimiesiaca
        if (!weryfikujWartosci(dopuszczalneDniMiesiaca, podzialDniMiesiaca)) {
            System.out.println("Podano bledne dni miesiaca " + dniMiesiaca);
            return;
        }
        //dniMiesiaca sa ok, zamieniam je na wartosci
        sheduleDayOfMonth = konwertujWartosc(dniMiesiaca, dopuszczalneDniMiesiaca);

        String dniTygodnia = podzielonaLinia[3];
        String[] podzialDniTygodnia = dniTygodnia.split(",");
        //weryfikacja dniTygodnia
        if (!weryfikujWartosci(dopuszczalneDniTygpdnia, podzialDniTygodnia)) {
            System.out.println("Podano bledne dni tygodnia " + dniTygodnia);
            return;
        }
        //dniTygodnia sa ok, zamieniam je na wartosci
        sheduleDayOfWeek = konwertujWartosc(dniTygodnia, dopuszczalneDniTygpdnia);

        System.out.print("Minuty: ");
        for (String min : sheduleMinute) {
            if (min != null)
                System.out.print(min + " ");
        }
        System.out.println();

        System.out.print("Godziny: ");
        for (String godz : sheduleHour) {
            if (godz != null)
                System.out.print(godz + " ");
        }
        System.out.println();

        System.out.print("dzień miesiąca: ");
        for (String dzienM : sheduleDayOfMonth) {
            if (dzienM != null)
                System.out.print(dzienM + " ");
        }
        System.out.println();

        System.out.print("dzień tygodnia: ");
        for (String dzienTyg : sheduleDayOfWeek) {
            if (dzienTyg != null)
                System.out.print(dzienTyg + " ");
        }
        System.out.println();
    }


}
