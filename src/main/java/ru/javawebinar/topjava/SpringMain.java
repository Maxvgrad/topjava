package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
//         java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email", "password", Role.ROLE_ADMIN));
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.save(new Meal(LocalDateTime.now(), "diner", 250));
        }

//        System.out.println(isShuffle("ab", "ba"));


//        int[] array = new int[1];
//
//        for(int i = array.length - 1;i >= 0; i++) {
//            System.out.println(i);
//            array[i] = i;
//        }
    }

 /*   public static Integer counter(Integer start, Integer end) {
        int counter = 0;
        int tmp;
        for(int i = start; i <= end; i++) {
            tmp = i;
            do {
                if (tmp % 10 == 3 || tmp == 3)
                    counter++;
                tmp = tmp / 10;
            } while(tmp != 0);
        }

        return counter;
    }

    public static boolean findOudIfTree(Node node) {
        Set<Integer> setID = new HashSet<>();
        return iterrateTheTree(node, setID);
    }

    private static boolean iterrateTheTree(Node node, Set<Integer> setID) {
        if (setID.contains(node.nodeID))
            return Boolean.FALSE;

        if (node.nodeRef == null)
            return Boolean.TRUE;

        setID.add(node.nodeID);

        return iterrateTheTree(node.nodeRef, setID);
    }

    public static class Node {
        public Integer nodeID;
        public Node nodeRef;
    }

    public static boolean isShuffle(String strOne, String strTwo) {
        if (strOne.length() != strTwo.length())
            return Boolean.FALSE;

        if (strOne == null || strTwo == null)
            return Boolean.FALSE;

        ArrayList<Character> listOne = new ArrayList<>();
        ArrayList<Character> listTwo = new ArrayList<>();

        for (char c : strOne.toCharArray())
            listOne.add(c);

        for (char c : strTwo.toCharArray())
            listTwo.add(c);

        if (listOne.containsAll(listTwo))
            return Boolean.TRUE;
        return Boolean.FALSE;

        new HashSet<>()
    }


    public static Integer[][] nuller(Integer[][] matrix) {
        Integer[][] result = new Integer[matrix.length][matrix[matrix.length].length];

        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[matrix.length].length; j++) {
                if(matrix[i][j] == 0) {
                    for(int row = 0; row < matrix[matrix.length].length; row++) {
                        result[i][row] = 0;
                    }
                    for(int clm = 0; clm < matrix.length; clm++) {
                        result[i][clm] = 0;
                    }
                } else {
                    result[i][j] = matrix[i][j];
                }
            }
        }
        return result;
    }*/
}
