package operation;

import features.Admin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdminOperation {

    private static AdminOperation instance;

    /**
    * Returns the single instance of AdminOperation.
    * @return AdminOperation instance
    **/

    public static AdminOperation getInstance(){
        if(instance == null){
            instance = new AdminOperation();
        }
        return instance;
    }

    /**
    * Creates an admin account. This function should be called when
    * the system starts. The same admin account should not be
    * registered multiple times.
    * The registration is based on the assignment of Admin class (which is also inherites from User class)
    **/

    public void registerAdmin() {
        File userFile = new File("database\\users.txt");
        userFile.getParentFile().mkdirs();

        // Only check the file if it exists
        if (userFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("\"user_id\":\"u_0000000001\"") && line.contains("\"user_role\":\"admin\"")) {
                        return; // Admin already registered
                    }
                }
            } catch (IOException e) {
                return; // Silent failure as requested
            }
        }

        // Create Admin object using setters
        Admin defaultAdmin = new Admin();
        try {
            defaultAdmin.setUserId("u_0000000001");
            defaultAdmin.setUserName("admin");
            defaultAdmin.setUserPassword("admin123"); // will remain encrypted as-is
            defaultAdmin.setUserRegisterTime(new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(new Date()));
            defaultAdmin.setUserRole("admin");
        } catch (IllegalArgumentException e) {
            return; // Silent failure on invalid data
        }

        // Write to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile, true))) {
            writer.write(defaultAdmin.toString());
            writer.newLine();
        } catch (IOException ignored) {
            // Silent failure
        }
    }



    /*
    public void registerAdmin(){
        File userFile = new File("ecommerce-system-cli/database/users.txt");
        userFile.getParentFile().mkdirs();
        String defaultAdminId = "u_0000000001";

        try(BufferedReader reader = new BufferedReader(new FileReader(userFile))){
            String line;
            while((line = reader.readLine()) != null){
                if(line.contains("\"user_id\":\"" + defaultAdminId + "\"") && line.contains("\"user_role\":\"admin\"")){
                    return; //The Admin account is already registered
                }
            }
        } catch (IOException ignored){
            //Leave blank
        }

        String registerTime = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(new Date());
        Admin defaultAdmin = new Admin(defaultAdminId, "admin", "admin123", registerTime, "admin");

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(userFile, true))){
            writer.write(defaultAdmin.toString());
            writer.newLine();
        } catch (IOException e){
            e.printStackTrace();
        }

    }
    */

}
