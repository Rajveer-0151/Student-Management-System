import java.lang.classfile.instruction.ReturnInstruction;
import java.sql.SQLOutput;
import java.util.Scanner;

public class SMS {
    static Scanner sc = new Scanner(System.in);
    static Boolean deleted = false;
    static Boolean updated = false;
    static double tavg, bavg, gavg;
    static int bcount,gcount;
    static Object[][] bdata = new Object[0][7];
    static Object[][] gdata = new Object[0][7];

    public static void main(String[] args) {
        int input1 = 0, input2 = 0, input3 = 0;
        int no_Student = 0;
        boolean loop1 = true, loop2 = true,loopa=true;
        Object[][] data = new Object[no_Student][7];

        while (loop1) {
            System.out.println("1->Add Student");
            System.out.println("2->More Option");
            System.out.println("3->Exit");
            while (true) {
                if (sc.hasNextInt()) {
                    input1 = sc.nextInt();
                    break;
                } else {
                    System.out.println("Invalid Input!,please enter a valid number");
                    sc.next();
                }
            }
            switch (input1) {
                case 1 -> {
                    while (true) {
                        System.out.println("Enter the number of Student to add");
                        if (sc.hasNextInt()) {
                            input2 = sc.nextInt();
                            sc.nextLine();
                            if (input2 > 0) {
                                Object[][] expandedData = new Object[no_Student + input2][7];
                                for (int i = 0; i < no_Student; i++) {
                                    expandedData[i] = data[i];
                                }
                                data = add(input2, expandedData, no_Student);
                                no_Student += input2;
                                break;
                            }
                            System.out.println("Please enter a number greater than 0");

                        } else {
                            System.out.println("Invalid Number!, Enter again");
                            sc.next();

                        }
                    }
                }
                case 2 -> {
                    while(loopa){
                        System.out.println("1->Search Student Data");
                        System.out.println("2->Delete Student Data");
                        System.out.println("3->Update Student Data");
                        System.out.println("4->View All Student Data");
                        System.out.println("5->Display Statistics");
                        System.out.println("6->Main menu");
                        while (true) {
                            if (sc.hasNextInt()) {
                                input3 = sc.nextInt();
                                sc.nextLine();
                                break;
                            } else {
                                System.out.println("please enter a valid number");
                                sc.next();
                            }
                        }
                        switch (input3) {
                            case 1 -> {
                                String found = search(data, no_Student);
                                System.out.println(found);
                            }
                            case 2 -> {
                                data = delete(data, no_Student);
                                if (deleted) {
                                    System.out.println("Data Deleted Sucessfully");
                                    no_Student--;
                                }
                            }
                            case 3 -> {
                                data = update(data, no_Student);
                                if (updated) {
                                    System.out.println("Data Upadated Sucessfully");
                                }

                            }
                            case 4 -> {
                                viewall(data, no_Student);

                            }
                            case 5 -> {statsmenu(data,no_Student);
                            }
                            case 6 -> loopa=false;
                            default -> System.out.println("Enter from 1 to 5");}

                    }

                }
                case 3 -> {
                    System.out.println("Thanks For Using");
                    loop1 = false;
                }
            }
        }

    }

    static Object[][] add(int x, Object[][] data, int startIndex) {
        String name, gen;
        for (int i = startIndex; i < startIndex + x; i++) {
            //name
            System.out.print((i + 1) + "] Enter Name ");
            data[i][0] = sc.nextLine();
            //gender
            System.out.println("Enter gender (M/F)");
            data[i][1] = sc.nextLine().toUpperCase().trim();
            while (!data[i][1].equals("M") && !data[i][1].equals("F")) {
                System.out.println("Please enter from (M,F)");
                data[i][1] = sc.nextLine().toUpperCase().trim();
            }
            //roll no.
            System.out.println("Enter Roll no.");
            while (true) {
                if (sc.hasNextInt()) {
                    data[i][2] = sc.nextInt();
                    break;
                } else {
                    System.out.println("Invalid Number,Enter a valid number");
                    sc.next();
                }

            }
            //pcm
            //physics
            data[i][3] = readMarks("Physics");
            data[i][4] = readMarks("Chemistry");
            data[i][5] = readMarks("Maths");
            data[i][6] = (int) data[i][3]
                    + (int) data[i][4]
                    + (int) data[i][5];
        }
        return data;

    }

    static int readMarks(String subject) {
        System.out.println("Enter Your " + subject + " Marks");
        while (true) {
            if (sc.hasNextInt()) {
                int marks = sc.nextInt();
                sc.nextLine();
                if (marks > 0 && marks <= 100) {
                    return marks;
                }
                System.out.println("Marks must be greater than 0 and less than 101");
            } else {
                System.out.println("Invalid Number,Enter a valid number");
                sc.next();
            }
        }
    }

    static String search(Object[][] data, int no) {
        String Name;
        int input4, temp1, temp2;
        System.out.println("1->Search Using Roll no.");
        System.out.println("2->Search Using Name");
        while (true) {
            if (sc.hasNextInt()) {
                input4 = sc.nextInt();
                sc.nextLine();
                break;
            } else {
                System.out.println("Enter a valid Number");
                sc.next();
            }
        }
        switch (input4) {
            case 1 -> {
                System.out.println("Enter the roll number");
                while (true) {
                    if (sc.hasNextInt()) {
                        temp1 = sc.nextInt();
                        sc.nextLine();
                        break;
                    } else {
                        System.out.println("Invalid NUmber");
                        sc.next();
                    }
                }
                for (int i = 0; i < no; i++) {
                    if (data[i][2] != null && data[i][2].equals(temp1)) {
                        System.out.println("Student Found");
                        return "Name: " + data[i][0] +
                                "\nGender:" + data[i][1] +
                                "\nRoll no.:" + data[i][2] +
                                "\nPhysics Marks:" + data[i][3] +
                                "\nChemisty Marks:" + data[i][4] +
                                "\nMaths Marks:" + data[i][5] +
                                "\nTotal Marks:" + data[i][6];

                    }
                }

            }
        }

        return "Student Not Found ";
    }

    static Object[][] delete(Object[][] data, int no) {
        int del = -1, roll1;
        System.out.println("Enter the roll Number to Delete");
        while (true) {
            if (sc.hasNextInt()) {
                roll1 = sc.nextInt();
                sc.nextLine();
                break;
            } else {
                System.out.println("Enter a Valid Roll no.");
                sc.next();
            }
        }
        for (int i = 0; i < no; i++) {
            if (data[i][2].equals(roll1) && data[i][2] != null) {
                del = i;
                deleted = true;
            }
        }
        if (del == -1) {
            System.out.println("Student Not Found");
            deleted = false;
            return data;
        }

        for (int i = del; i < no - 1; i++) {
            data[i] = data[i + 1];

        }
        data[no - 1] = new Object[7];

        return data;
    }

    static Object[][] update(Object[][] data, int no) {
        int temp1, temp2 = -1, temp3;
        System.out.println("Enter the Student Roll no.");
        while (true) {
            if (sc.hasNextInt()) {
                temp1 = sc.nextInt();
                sc.nextLine();
                break;
            } else {
                System.out.println("Enter A valid roll no.");
                sc.next();
            }
        }
        for (int i = 0; i < no; i++) {
            if (data[i][2].equals(temp1)) {
                temp2 = i;
                System.out.println("Student Found");
                updated = true;
            }
        }
        if (temp2 == -1) {
            System.out.println("Student not found");
            updated = false;
        } else {
            while (true) {
                System.out.println("What to Update");
                System.out.println("1->Name");
                System.out.println("2->Gender");
                System.out.println("3->Roll no.");
                System.out.println("4->Physics Marks");
                System.out.println("5->Chemistry Marks");
                System.out.println("6->Maths Marks");
                System.out.println("6->Maths Marks");
                System.out.println("7->Exit");
                while (true) {
                    if (sc.hasNextInt()) {
                        temp3 = sc.nextInt();
                        sc.nextLine();
                        break;
                    } else {
                        System.out.println("Invalid Input!!! Enter a valid Number");
                        sc.next();
                    }
                }
                switch (temp3) {
                    case 1 -> {
                        System.out.println("Enter the Name");
                        data[temp2][0] = sc.nextLine();
                    }
                    case 2 -> {
                        System.out.println("Enter gender (M/F)");
                        data[temp2][1] = sc.nextLine().toUpperCase().trim();
                        while (!data[temp2][1].equals("M") && !data[temp2][1].equals("F")) {
                            System.out.println("Please enter from (M,F)");
                            data[temp2][1] = sc.nextLine().toUpperCase().trim();
                        }
                    }
                    case 3 -> {
                        System.out.println("Enter Roll no.");
                        while (true) {
                            if (sc.hasNextInt()) {
                                data[temp2][2] = sc.nextInt();
                                break;
                            } else {
                                System.out.println("Invalid Number,Enter a valid number");
                                sc.next();
                            }

                        }
                    }
                    case 4 -> {
                        data[temp2][3] = readMarks("Physics");
                    }
                    case 5 -> {
                        data[temp2][4] = readMarks("Chemistry");
                    }
                    case 6 -> {
                        data[temp2][5] = readMarks("Maths");
                    }
                    case 7 -> {
                        data[temp2][6] = (int) data[temp2][3]
                                + (int) data[temp2][4]
                                + (int) data[temp2][5];
                        return data;
                    }
                    default -> System.out.println("Enter From(1 to 7)");
                }
            }

        }
        return data;
    }

    static void viewall(Object[][] data, int no) {
        if (no == 0) {
            System.out.println("No student Record Found");
            return;
        }
        Object[][] sorteddata = new Object[no][];
        for (int i = 0; i < no; i++) {
            sorteddata[i] = data[i];
        }
        sort(sorteddata, no, 2);
        System.out.println("-------------------------- Student Records --------------------------");

        System.out.printf("%-15s %-8s %-10s %-12s %-12s %-12s %-12s%n",
                "Name", "Gender", "Roll No.", "Phy Marks",
                "Chem Marks", "Maths Marks", "Total Marks");

        System.out.println("--------------------------------------------------------------------------------");

        for (int i = 0; i < no; i++) {
            System.out.printf("%-15s %-8s %-10s %-12s %-12s %-12s %-12s%n",
                    sorteddata[i][0], sorteddata[i][1], sorteddata[i][2],
                    sorteddata[i][3], sorteddata[i][4], sorteddata[i][5], sorteddata[i][6]);
        }

        System.out.println("Total number of Students = " + no);
        return;
    }

    static void statsmenu(Object[][] data, int no) {
        int input1;
        boolean loopb=true;
        while (loopb){
            System.out.println("---------Student Statistics---------");
            System.out.println("1->Class Performance");
            System.out.println("2->Student Below Average");
            System.out.println("3->Passed / Failed Student");
            System.out.println("4->Subject wise performance");
            System.out.println("5->Student rank list");
            System.out.println("6->Back");
            while (true) {
                if (sc.hasNextInt()) {
                    input1 = sc.nextInt();
                    sc.nextLine();
                    break;
                } else {
                    System.out.println("Enter a valid Number");
                    sc.next();
                }
            }
            switch (input1) {
                case 1 -> {
                    CP(data, no);
                }
                case 2-> sba(data,no);
                case 3-> pf(data,no);
                case 4-> swp(data,no);
                case 5->srl(data,no);
                case 6->loopb=false;
                default -> System.out.println("Enter From (1 to 6)");
            }
        }
    }

    static Object[][] sort(Object[][] data, int no, int a) {

        for (int i = 0; i < no - 1; i++) {
            for (int j = 0; j < no - 1 - i; j++) {
                if ((int) data[j][a] > (int) data[j + 1][a]) {
                    Object[] temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                }
            }
        }
        return data;
    }

    static void prepareGenderData(Object[][] data, int no) {
        bcount = 0;
        gcount = 0;
        for (int i = 0; i < no; i++) {
            if (data[i][1].equals("M")) {
                bcount++;
            } else {
                gcount++;
            }
        }
        bdata = new Object[bcount][7];
        gdata = new Object[gcount][7];
        int boysIndex = 0;
        int girlsIndex = 0;
        for (int i = 0; i < no; i++) {
            if (data[i][1].equals("M")) {
                bdata[boysIndex++] = data[i];
            } else {
                gdata[girlsIndex++] = data[i];
            }
        }
    }

    static void CP(Object[][] data, int no) {
        float totalsum = 0, bsum = 0, gsum = 0;
        prepareGenderData(data, no);
        for (int i = 0; i < no; i++) {
            totalsum += (int) data[i][6];
            if (data[i][1].equals("M")) {
                bsum += (int) data[i][6];
            } else {
                gsum += (int) data[i][6];
            }
        }
        if (no == 0) {
            System.out.println("No student Record Found");
            return;
        }
        sort(data, no, 6);
        sort(bdata, bcount, 6);
        sort(gdata, gcount, 6);
        tavg = totalsum / no;
        bavg = bcount == 0 ? 0 : bsum / bcount;
        gavg = gcount == 0 ? 0 : gsum / gcount;

        System.out.println("==================================================");
        System.out.println("             STUDENT ACHIEVEMENTS");
        System.out.println("==================================================");
        System.out.println("\n             Class topper");
        System.out.println("------------------------------------------");
        System.out.printf("%-15s : %s%n", "Name", data[no - 1][0]);
        System.out.printf("%-15s : %s%n", "Roll no.", data[no - 1][2]);
        System.out.printf("%-15s : %s%n", "Total Marks :", data[no - 1][6]);
        System.out.printf("%-15s : %s%n", "Percentage", ((Number) data[no - 1][6]).doubleValue() / 3);
        System.out.println("------------------------------------------");
        if (bcount > 0) {
            System.out.println("\n             Boys topper");
            System.out.println("------------------------------------------");
            System.out.printf("%-15s : %s%n", "Name", bdata[bcount - 1][0]);
            System.out.printf("%-15s : %s%n", "Roll no.", bdata[bcount - 1][2]);
            System.out.printf("%-15s : %s%n", "Total Marks :", bdata[bcount - 1][6]);
            System.out.printf("%-15s : %s%n", "Percentage", ((Number) bdata[bcount - 1][6]).doubleValue() / 3);
            System.out.println("------------------------------------------");
        } else {
            System.out.println("\n             Boys topper: No boys found");
        }
        if (gcount > 0) {
            System.out.println("\n             Girls topper");
            System.out.println("------------------------------------------");
            System.out.printf("%-15s : %s%n", "Name", gdata[gcount - 1][0]);
            System.out.printf("%-15s : %s%n", "Roll no.", gdata[gcount - 1][2]);
            System.out.printf("%-15s : %s%n", "Total Marks :", gdata[gcount - 1][6]);
            System.out.printf("%-15s : %s%n", "Percentage", ((Number) gdata[gcount - 1][6]).doubleValue() / 3);
            System.out.println("------------------------------------------");
        } else {
            System.out.println("\n             Girls topper: No girls found");
        }
        System.out.println("Class Average :" + totalsum / no);
        System.out.println("Boy Average :" + bavg);
        System.out.println("Girl Average :" + gavg);
        System.out.println("--------------------------------------------------------------------------------");
        return;
    }

    static void sba(Object[][] data, int no) {
        int temp1;
        float totalsum = 0, bsum = 0, gsum = 0;
        for (int i = 0; i < no; i++) {
            totalsum += (int) data[i][6];
            if (data[i][1].equals("M")) {
                bsum += (int) data[i][6];
            } else {
                gsum += (int) data[i][6];
            }
        }
        sort(data, no, 6);
        sort(bdata, bcount, 6);
        sort(gdata, gcount, 6);
        tavg = totalsum / no;
        bavg = bcount == 0 ? 0 : bsum / bcount;
        gavg = gcount == 0 ? 0 : gsum / gcount;
        System.out.println("-------------------------- Student Below Average ---------------------------");
        System.out.printf("%-15s %-8s %-10s %-12s %-12s %-12s %-12s%n",
                "Name", "Gender", "Roll No.", "Phy Marks",
                "Chem Marks", "Maths Marks", "Total Marks");
        System.out.println("--------------------------------------------------------------------------------");
        for (int i = 0; i < no; i++) {
            if ((int) data[i][6] < tavg) {
                System.out.printf("%-15s %-8s %-10s %-12s %-12s %-12s %-12s%n",
                        data[i][0], data[i][1], data[i][2],
                        data[i][3], data[i][4], data[i][5], data[i][6]);
            }
        }
        System.out.println("--------------------------------------------------------------------------------");
        return;
    }

    static void pf(Object[][] data, int no) {
        int input1, count1 = 0, count2 = 0;
        System.out.println("1-> List of Student Passed ");
        System.out.println("2-> List of Student Failed ");
        while (true) {
            if (sc.hasNextInt()) {
                input1 = sc.nextInt();
                sc.nextLine();
                break;
            } else {
                System.out.println("Invalid Input!,Please Enter A Valid Number.");
                sc.next();
            }
        }
        if (input1 == 1) {
            System.out.println("-------------------------- PASSED STUDENTS LIST --------------------------");
            System.out.printf("%-15s %-8s %-10s %-12s %-12s %-12s %-12s%n",
                    "Name", "Gender", "Roll No.", "Phy Marks",
                    "Chem Marks", "Maths Marks", "Total Marks");
            System.out.println("--------------------------------------------------------------------------------");
            for (int i = 0; i < no; i++) {
                if ((int) data[i][3] >= 35 && (int) data[i][4] >= 35 && (int) data[i][5] >= 35) {
                    System.out.printf("%-15s %-8s %-10s %-12s %-12s %-12s %-12s%n",
                            data[i][0], data[i][1], data[i][2],
                            data[i][3], data[i][4], data[i][5], data[i][6]);
                    count1++;
                }

            }
            System.out.println("Number Of Student Who have passed :" + count1);
            System.out.println("--------------------------------------------------------------------------------");

        } else {
            System.out.println("-------------------------- FAILED STUDENTS LIST --------------------------");
            System.out.printf("%-15s %-8s %-10s %-12s %-12s %-12s %-12s%n",
                    "Name", "Gender", "Roll No.", "Phy Marks",
                    "Chem Marks", "Maths Marks", "Total Marks");
            System.out.println("--------------------------------------------------------------------------------");
            for (int i = 0; i < no; i++) {
                if ((int) data[i][3] < 35 || (int) data[i][4] < 35 || (int) data[i][5] < 35) {
                    System.out.printf("%-15s %-8s %-10s %-12s %-12s %-12s %-12s%n",
                            data[i][0], data[i][1], data[i][2],
                            data[i][3], data[i][4], data[i][5], data[i][6]);
                    count2++;

                }

            } System.out.println("Number Of Student Who have failed :" + count2);
            System.out.println("--------------------------------------------------------------------------------");
        }
        return;
    }

    static void swp(Object[][] data, int no) {
        int input1;
        System.out.println("Select the Subject");
        System.out.println("1->Physics");
        System.out.println("2->Chemistry");
        System.out.println("3->Maths");
        while (true) {
            if (sc.hasNextInt()) {
                input1 = sc.nextInt();
                sc.nextLine();
                break;
            } else {
                System.out.println("Enter a valid number");
                sc.next();
            }
        }
        switch (input1) {
            case 1 -> {
                swp2(data,no,input1,"Physics");

            }
            case 2-> swp2(data,no,input1,"Chemistry");
            case 3 -> swp2(data,no,input1,"Maths");
        }
        return;
    }

    static void swp2(Object[][] data, int no,int input1, String subject) {
        if (no == 0) {
            System.out.println("No student Record Found");
            return;
        }
        prepareGenderData(data, no);
        data= sort(data, no, input1+2);
        bdata= sort(bdata,bcount,input1+2);
        gdata= sort(gdata,gcount,input1+2);
        int count1=0,sum1=0;
        int count2=0,sum2=0;
        int count3=0,sum3=0;

        System.out.printf("%-15s %-8s %-10s %-12s %-12s %-12s %-12s%n",
                "Name", "Gender", "Roll No.", "Phy Marks",
                "Chem Marks", "Maths Marks", "Total Marks");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println(subject + " Topper :");
        System.out.printf("%-15s %-8s %-10s %-12s %-12s %-12s %-12s%n",
                data[no - 1][0], data[no - 1][1], data[no - 1][2],
                data[no - 1][3], data[no - 1][4], data[no - 1][5], data[no - 1][6]);
        if (bcount > 0) {
            System.out.println(subject + " Topper From Boys :");
            System.out.printf("%-15s %-8s %-10s %-12s %-12s %-12s %-12s%n",
                    bdata[bcount - 1][0], bdata[bcount - 1][1], bdata[bcount - 1][2],
                    bdata[bcount - 1][3], bdata[bcount - 1][4], bdata[bcount - 1][5],bdata[bcount - 1][6]);
        } else {
            System.out.println(subject + " Topper From Boys : No boys found");
        }
        if (gcount > 0) {
            System.out.println(subject + " Topper From Girls :");
            System.out.printf("%-15s %-8s %-10s %-12s %-12s %-12s %-12s%n",
                    gdata[gcount - 1][0], gdata[gcount - 1][1], gdata[gcount - 1][2],
                    gdata[gcount - 1][3], gdata[gcount - 1][4], gdata[gcount - 1][5],gdata[gcount - 1][6]);
        } else {
            System.out.println(subject + " Topper From Girls : No girls found");
        }
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("\n");
        System.out.println("Passing Percentage In "+subject+" Exam:");
        for (int i =0;i<no;i++){
            sum1+=(int)data[i][input1+2];
            if ((int)data[i][input1+2]>=35){
                count1++;
            }
        } for (int i =0;i<bcount;i++){
            sum2+=(int)bdata[i][input1+2];
            if ((int)bdata[i][input1+2]>=35){
                count2++;
            }
        }for (int i =0;i<gcount;i++){
            sum3+=(int)gdata[i][input1+2];
            if ((int)gdata[i][input1+2]>=35){
                count3++;
            }
        }
        System.out.println("Total :"+count1*100/no +"%");
        System.out.println("From Boys :"+(bcount == 0 ? 0 : count2*100/bcount)+"%");
        System.out.println("From Girls :"+(gcount == 0 ? 0 : count3*100/gcount)+"%");
        System.out.println("\n");
        System.out.println("Average Marks In "+subject+" :");
        System.out.println("Total Class :"+sum1/no);
        System.out.println("Boys :"+(bcount == 0 ? 0 : sum2/bcount));
        System.out.println("girls:"+(gcount == 0 ? 0 : sum3/gcount));
        return;


    }

    static void srl(Object[][] data, int no){
        int input1=0;
        data= sort(data,no,6);
        System.out.println("Enter the number of Student to Display");
        while (true) {
            if (sc.hasNextInt()) {
                input1=sc.nextInt();
                sc.nextLine();
                if (input1>=1 && input1<=no){
                    break;}
                else{
                    System.out.println("Enter the Number between 1 to "+no);
                    sc.next();
                }

            }else {
                System.out.println("Enter A valid Number");
                sc.next();
            }
        }
        System.out.println("----------------------- Student Rank List ----------------------");
        System.out.printf("%-15s %-8s %-10s %-12s %-12s %-12s %-12s%n",
                "Name", "Gender", "Roll No.", "Phy Marks",
                "Chem Marks", "Maths Marks", "Total Marks");

        System.out.println("--------------------------------------------------------------------------------");

        for (int i = 0; i <input1 ; i++) {
            System.out.printf("%-15s %-8s %-10s %-12s %-12s %-12s %-12s%n",
                    data[no-1-i][0], data[no-i-1][1], data[no-i-1][2],
                    data[no-i-1][3], data[no-1-i][4], data[no-i-1][5], data[no-i-1][6]);

        }System.out.println("--------------------------------------------------------------------------------");
    }

}
