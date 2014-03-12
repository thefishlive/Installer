-libraryjars <java.home>/lib/rt.jar
-printmapping build/proguard/mappings.maps

-dontwarn javax.annotation.**
-dontwarn javax.inject.**

# Keep - Applications. Keep all application classes, along with their 'main'
# methods.
-keepclasseswithmembers public class ** {
    public static void main(java.lang.String[]);
}

-keep,allowshrinking class ** {
    void launch(...);
}

# Also keep - Enumerations. Keep the special static methods that are required in
# enumeration classes.
-keep,allowshrinking enum  ** {
    <fields>;
    <methods>;
}

# Also keep - Swing UI L&F. Keep all extensions of javax.swing.plaf.ComponentUI,
# along with the special 'createUI' method.
-keep class * extends javax.swing.plaf.ComponentUI {
    public static javax.swing.plaf.ComponentUI createUI(javax.swing.JComponent);
}

# Keep names - Native method names. Keep all native class/method names.
-keepclasseswithmembers,allowshrinking class * {
    native <methods>;
}
