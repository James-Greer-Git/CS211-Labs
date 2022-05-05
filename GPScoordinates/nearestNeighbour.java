import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class tsp {

    public static void main(String args[]) throws IOException{
        File file = new File("schools.txt");
        List<List<String>> schools = readSchoolsFromFile(file);
        System.out.println("School coordinates added to list");

        List<List<Double>> distances = new ArrayList<>();

        //create array to hold distances between all schools
        for(int i = 0; i < schools.size(); i++){
            double latitude1 = Double.parseDouble(schools.get(i).get(0));
            double longitude1 = Double.parseDouble(schools.get(i).get(1));
            List<Double> schoolsi = new ArrayList<>();
            for(int j = 0; j < schools.size(); j++){
                double latitude2 = Double.parseDouble(schools.get(j).get(0));
                double longitude2 = Double.parseDouble(schools.get(j).get(1));
                schoolsi.add(distanceInKilometres(latitude1, longitude1, latitude2, longitude2));
            }
            distances.add(schoolsi);
        }

        String path = new String("0");
        String gpsPath = new String("");

        //***************************************************************************************/
        //nearest neighbour
        List<Integer> visitedIds = new ArrayList<>();
        int currentId = 0;
        visitedIds.add(currentId);
        double totalDistanceInKm = 0;

        while(visitedIds.size() < schools.size()){
            double minDistance = Double.MAX_VALUE;
            int minDistanceIndex = 0;
            //loop to find nearest neighbour to current school
            for(int i = 0; i < distances.get(currentId).size(); i++){      
                if(distances.get(currentId).get(i) < minDistance && currentId != i && !visitedIds.contains(i)){
                    minDistance = distances.get(currentId).get(i);
                    minDistanceIndex = i;
                }
            }
            //adding nearest index to path and counting it as visited
            path += "," + minDistanceIndex;
            gpsPath += schools.get(minDistanceIndex).get(0) + "," + schools.get(minDistanceIndex).get(1) + "\n";
            totalDistanceInKm += distances.get(currentId).get(minDistanceIndex);
            visitedIds.add(minDistanceIndex);
            currentId = minDistanceIndex;
        }
        //***************************************************************************************/

        path += ",0";
        totalDistanceInKm += distances.get(0).get(currentId);
        gpsPath += schools.get(0).get(0) + "," + schools.get(0).get(1) + "\n";
        String pathtxt = "gpsPath.txt";

        System.out.println(path);
        Files.writeString(Paths.get(pathtxt), gpsPath);
        System.out.println(totalDistanceInKm);
    }

    public static double distanceInKilometres(double latitude1, double longitude1, double latitude2, double longitude2){
        int radius = 6371;
        
        double lat1 = Math.toRadians(latitude1);
        double lon1 = Math.toRadians(longitude1);
        double lat2 = Math.toRadians(latitude2);
        double lon2 = Math.toRadians(longitude2);

        return radius * Math.acos(Math.sin(lat1)*Math.sin(lat2) + Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon1 - lon2));
      }

    public static  double haversine(double a){
        return (Math.sin(a/2))*(Math.sin(a/2));
    }

    public static List<List<String>> readSchoolsFromFile(File file) throws FileNotFoundException {
        List<List<String>> schools = new ArrayList<>();
        Scanner scanner = new Scanner(file);

        //reads text file and seperates latitude and longitude using split()
        while(scanner.hasNext()){
            String line = scanner.next();
            List<String> res = Arrays.asList(line.split("[,]", 0));
            schools.add(res);
        }
        scanner.close();

        return schools;
    }
}
