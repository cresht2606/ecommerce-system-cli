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
    
    public void registerAdmin(){
        File userFile = new File("ecommerce-system-cli/database/users.txt");
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
        Admin defaultAdmin = new Admin(defaultAdminId, "admin", "admin123", registerTime);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(userFile, true))){
            writer.write(defaultAdmin.toString());
            writer.newLine();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

}
