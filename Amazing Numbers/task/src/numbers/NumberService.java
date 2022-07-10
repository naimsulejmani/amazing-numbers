package numbers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class NumberService {
    private BigInteger number;
    private BigInteger iter;
    private ArrayList<Properties> properties;

    private ArrayList<Properties> excludeProperties;

    private ArrayList<String> wrongProperties;
    private int params = 2;

    public NumberService(String number) {
        this(number, null, new String[0]);
        this.params = 1;
    }


    public NumberService(String number, String iter) {
        this(number, iter, new String[0]);
        this.params = 2;
    }

    public NumberService(String number, String iter, String[] properties) {
        setNumber(number);
        setIter(iter);
        this.params = this.params + properties.length;
        this.properties = new ArrayList<>();
        this.excludeProperties = new ArrayList<>();
        this.wrongProperties = new ArrayList<>();
        initializeProperties(properties);
    }

    private void initializeProperties(String[] properties) {
        if (properties == null || properties.length == 0) return;
        for (String property : properties) {
            try {
                Properties prop = null;
                if (property.startsWith("-")) {
                    prop = Properties.valueOf(property.substring(1).toUpperCase());
                    excludeProperties.add(prop);
                } else {
                    prop = Properties.valueOf(property.toUpperCase());
                    this.properties.add(prop);
                }

            } catch (Exception ex) {
                wrongProperties.add(property);
            }
        }
    }


    public void start() {
        switch (params) {
            case 1:
                processOne();
                break;
            case 2:
                processTwo();
                break;
            default:
                processMore();
                break;
        }
    }

    private void processMore() {
        if (wrongProperties.size() > 0) {
            if (wrongProperties.size() == 1) {
                System.out.printf("The property [%s] is wrong.%n", wrongProperties.get(0));
            } else {
                System.out.printf("The properties ... are wrong%n");
            }
            System.out.printf("Available properties: %s%n", Arrays.toString(Properties.values()));
            return;
        } else if (Properties.isMutualExclusiveProperty(properties)) {
            System.out.printf("The request contains mutually exclusive properties: [%s]%n", Properties.getMutualExclusiveProperty(properties));
            System.out.println("There are no numbers with these properties.");
            return;
        } else if (Properties.isMutualExcludedExclusiveProperty(excludeProperties)) {
            System.out.printf("The request contains mutually exclusive properties: [%s]%n", Properties.getMutualExcludedExclusiveProperty(excludeProperties));
            System.out.println("There are no numbers with these properties.");
            return;
        } else if (properties != null && Properties.isMutualExclusiveProperty(properties, excludeProperties)) {
            Properties prop = properties.stream().filter(p -> excludeProperties.contains(p)).findFirst().get();
            System.out.printf("The request contains mutually exclusive properties: [%s, %s]%n", "-" + prop, prop);
            System.out.println("There are no numbers with these properties.");
            return;
        }
        BigInteger adder = BigInteger.ZERO;
        while (iter.intValue() > 0) {
            BigInteger no = number.add(adder);
            adder = adder.add(BigInteger.ONE);
            Number nr = new Number(no);
            if (properties.stream().allMatch(nr::checkProperties) && excludeProperties.stream().noneMatch(nr::checkProperties)) {
                nr.printTrueProperties();
                iter = iter.add(BigInteger.valueOf(-1));
            }
        }

    }

    private void processTwo() {
        for (int i = 0; i < iter.intValue(); i++) {
            BigInteger no = number.add(BigInteger.valueOf(i));
            if (no.compareTo(BigInteger.ZERO) < 0) {
                throw new ParameterException("The first parameter should be a natural number or zero.");
            }
            Number nr = new Number(no);
            nr.printTrueProperties();
        }
    }

    private void processOne() {
        System.out.println(new Number(number));
    }

    private Properties getProperty(String property) {
        for (Properties properties : Properties.values()) {
            if (properties.toString().equalsIgnoreCase(property))
                return properties;
        }
        return null;
    }


    private void setNumber(String number) {
        try {
            this.number = new BigInteger(number);
            if (this.number.compareTo(BigInteger.ZERO) < 0)
                throw new ParameterException("The first parameter should be a natural number or zero.");
        } catch (IllegalArgumentException ex) {
            if (ex instanceof ParameterException) throw ex;
            throw new ParameterException("The first parameter should be a natural number or zero.");
        }
    }

    private void setIter(String number) throws ParameterException {
        try {
            if (number == null) return;
            this.iter = new BigInteger(number);
            if (this.iter.compareTo(BigInteger.ZERO) < 0)
                throw new ParameterException("The second parameter should be a natural number.");
        } catch (IllegalArgumentException ex) {
            throw new ParameterException("The second parameter should be a natural number.");
        }
    }


}
