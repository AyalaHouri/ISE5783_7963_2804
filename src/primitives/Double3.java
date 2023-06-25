package primitives;

import static primitives.Util.isZero;

/**
 * This class represents a triple of double-precision floating-point numbers.
 * The class provides various mathematical operations such as addition, subtraction,
 * scaling, reduction, and product.
 * @author Ayala Houri and Shani Zegal
 */
public class Double3 {
   /** First number */
   final double                d1;
   /** Second number */
   final double                d2;
   /** Third number */
   final double                d3;

   /** Zero triad (0,0,0) */
   public static final Double3 ZERO = new Double3(0, 0, 0);

   /** One's triad (1,1,1) */
   public static final Double3 ONE  = new Double3(1, 1, 1);

   /**
    * Constructor to initialize a new Double3 object with its three number values
    * @param d1 The value of the first number
    * @param d2 The value of the second number
    * @param d3 The value of the third number
    */
   public Double3(double d1, double d2, double d3) {
      this.d1 = d1;
      this.d2 = d2;
      this.d3 = d3;
   }

   /**
    * Constructor to initialize a new Double3 object with the same number value for all 3 numbers
    * @param value The value of all 3 numbers
    */
   public Double3(double value) {
      this.d1 = value;
      this.d2 = value;
      this.d3 = value;
   }

   /**
    * Compares the current Double3 object to another object for equality.
    * Two Double3 objects are considered equal if their corresponding
    * numbers are "close enough" to each other.
    * @param obj The object to compare with
    * @return true if the objects are equal, false otherwise
    */
   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Double3 other = (Double3) o;
         return isZero(d1 - other.d1)
                 && isZero(d2 - other.d2)
                 && isZero(d3 - other.d3);
   }

   /**
    * Returns the hash code value for the Double3 object.
    * The hash code is computed as the sum of the three numbers, rounded to the nearest integer.
    * @return The hash code value for the Double3 object
    */
   @Override
   public int hashCode() {
      return (int) Math.round(d1 + d2 + d3);
   }

   /**
    * Returns a string representation of the Double3 object in the form of "(d1,d2,d3)".
    * @return A string representation of the Double3 object
    */
   @Override
   public String toString() {
      return "(" + d1 + "," + d2 + "," + d3 + ")";
   }

   /**
    * Adds the current Double3 object to another Double3 object and returns the result.
    * @param  rhs The right-hand side operand for addition
    * @return The result of the addition as a new Double3 object
    */
   public Double3 add(Double3 rhs) {
      return new Double3(d1 + rhs.d1, d2 + rhs.d2, d3 + rhs.d3);
   }

   /**
    * Subtract two floating point triads into a new triad where each couple of numbers is subtracted
    *
    * @param rhs The right-hand side operand for subtraction
    * @return The result of the subtraction as a new Double3 object
    */
   public Double3 subtract(Double3 rhs) {
      return new Double3(d1 - rhs.d1, d2 - rhs.d2, d3 - rhs.d3);
   }

   /**
    * Scale (multiply) floating point triad by a number into a new triad where each number is multiplied by the number
    *
    * @param rhs The right-hand side operand for scaling
    * @return The result of the scaling as a new Double3 object
    */
   public Double3 scale(double rhs) {
      return new Double3(d1 * rhs, d2 * rhs, d3 * rhs);
   }

   /**
    * Reduce (divide) floating point triad by a number into a new triad where each number is divided by the number
    *
    * @param rhs The right-hand side operand for division
    * @return The result of the division as a new Double3 object
    */
   public Double3 reduce(double rhs) {
      return new Double3(d1 / rhs, d2 / rhs, d3 / rhs);
   }

   /**
    * Product two floating point triads into a new triad where each couple of numbers is multiplied
    *
    * @param rhs The right-hand side operand for multiplication
    * @return The result of the multiplication as a new Double3 object
    */
   public Double3 product(Double3 rhs) {
      return new Double3(d1 * rhs.d1, d2 * rhs.d2, d3 * rhs.d3);
   }

   /**
    * Checks whether all the numbers are lower than a test number
    *
    * @param k The test number
    * @return True if all the numbers are less than k, false otherwise
    */
   public boolean lowerThan(double k) {
      return d1 < k && d2 < k && d3 < k;
   }


   /**
    * Checks whether all the numbers are lower than three numbers in another triad
    *
    * @param other The other Double3 object
    * @return True if all the numbers are less than the appropriate numbers in the other Double3 object, false otherwise
    */
   public boolean lowerThan(Double3 other) {
      return d1 < other.d1 && d2 < other.d2 && d3 < other.d3;
   }
   protected double getX() {return this.d1;}
   protected double getY() {return this.d2;}
   protected double getZ() {return this.d3;}

}
