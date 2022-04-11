import java.util.Scanner;

public class Solution{
    public static void main(String args[]){

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        double[][] inputs = new double[n][2];

        for(int i = 0; i < n; i++){
            inputs[i][0] = sc.nextDouble();
            inputs[i][1] = sc.nextDouble();
        }

        sc.close();

        for(int i = 0; i < n; i++){
            for(int j = 1; j < n; j++){
                coordinate c1 = new coordinate(inputs[i][0], inputs[i][1]);
                coordinate c2 = new coordinate(inputs[j][0], inputs[j][1]);

                System.out.println(distanceInKilometres(c1, c2));
            }
        }

        System.out.println("Finished");
    }

    public static double distanceInKilometres(coordinate c1, coordinate c2){

        int radius = 6371;
        
        double lat1 = Math.toRadians(c1.latitude);
        double lon1 = Math.toRadians(c1.longitude);
        double lat2 = Math.toRadians(c2.latitude);
        double lon2 = Math.toRadians(c2.longitude);

        double a = Math.sqrt(haversine(lat2 - lat1 + (1 - haversine(lat1 - lat2) - haversine(lat1 + lat2)) * haversine(lon2 - lon1)));

        double c = Math.asin(a);

        double distance = 2 * radius * c;

        return distance; //radius * Math.acos(Math.sin(lat1)*Math.sin(lat2) + Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon1 - lon2));

      }

    public static  double haversine(double a){
        return (Math.sin(a/2))*(Math.sin(a/2));
    }
}

class coordinate{
    double longitude;
    double latitude;

    coordinate(double aLongitude, double aLatitude){
        this.longitude = aLongitude;
        this.latitude = aLatitude;
    }
}
