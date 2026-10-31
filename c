[1mdiff --git a/src/main/java/controller/Laporan.form b/src/main/java/controller/Logs.form[m
[1msimilarity index 100%[m
[1mrename from src/main/java/controller/Laporan.form[m
[1mrename to src/main/java/controller/Logs.form[m
[1mdiff --git a/src/main/java/controller/Tagihan.java b/src/main/java/controller/Logs.java[m
[1msimilarity index 100%[m
[1mrename from src/main/java/controller/Tagihan.java[m
[1mrename to src/main/java/controller/Logs.java[m
[1mdiff --git a/src/main/java/controller/Riwayat.form b/src/main/java/controller/Pembayaran.form[m
[1msimilarity index 100%[m
[1mrename from src/main/java/controller/Riwayat.form[m
[1mrename to src/main/java/controller/Pembayaran.form[m
[1mdiff --git a/src/main/java/controller/Riwayat.java b/src/main/java/controller/Pembayaran.java[m
[1msimilarity index 100%[m
[1mrename from src/main/java/controller/Riwayat.java[m
[1mrename to src/main/java/controller/Pembayaran.java[m
[1mdiff --git a/src/main/java/controller/Tagihan.form b/src/main/java/controller/Users.form[m
[1msimilarity index 100%[m
[1mrename from src/main/java/controller/Tagihan.form[m
[1mrename to src/main/java/controller/Users.form[m
[1mdiff --git a/src/main/java/controller/Users.java b/src/main/java/controller/Users.java[m
[1mnew file mode 100644[m
[1mindex 0000000..d6c168d[m
[1m--- /dev/null[m
[1m+++ b/src/main/java/controller/Users.java[m
[36m@@ -0,0 +1,47 @@[m
[32m+[m[32mpackage controller;[m
[32m+[m
[32m+[m[32mpublic class Users extends javax.swing.JFrame {[m
[32m+[m[41m    [m
[32m+[m[32m    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Users.class.getName());[m
[32m+[m
[32m+[m[32m    public Users() {[m
[32m+[m[32m        initComponents();[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    @SuppressWarnings("unchecked")[m
[32m+[m[32m    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents[m
[32m+[m[32m    private void initComponents() {[m
[32m+[m
[32m+[m[32m        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);[m
[32m+[m
[32m+[m[32m        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());[m
[32m+[m[32m        getContentPane().setLayout(layout);[m
[32m+[m[32m        layout.setHorizontalGroup([m
[32m+[m[32m            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)[m
[32m+[m[32m            .addGap(0, 400, Short.MAX_VALUE)[m
[32m+[m[32m        );[m
[32m+[m[32m        layout.setVerticalGroup([m
[32m+[m[32m            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)[m
[32m+[m[32m            .addGap(0, 300, Short.MAX_VALUE)[m
[32m+[m[32m        );[m
[32m+[m
[32m+[m[32m        pack();[m
[32m+[m[32m    }// </editor-fold>//GEN-END:initComponents[m
[32m+[m
[32m+[m[32m    public static void main(String args[]) {[m
[32m+[m[32m        try {[m
[32m+[m[32m            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {[m
[32m+[m[32m                if ("Nimbus".equals(info.getName())) {[m
[32m+[m[32m                    javax.swing.UIManager.setLookAndFeel(info.getClassName());[m
[32m+[m[32m                    break;[m
[32m+[m[32m                }[m
[32m+[m[32m            }[m
[32m+[m[32m        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {[m
[32m+[m[32m            logger.log(java.util.logging.Level.SEVERE, null, ex);[m
[32m+[m[32m        }[m
[32m+[m[32m        java.awt.EventQueue.invokeLater(() -> new Users().setVisible(true));[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    // Variables declaration - do not modify//GEN-BEGIN:variables[m
[32m+[m[32m    // End of variables declaration//GEN-END:variables[m
[32m+[m[32m}[m
[1mdiff --git a/src/main/java/controller/list_mahasiswa.form b/src/main/java/controller/list_mahasiswa.form[m
[1mnew file mode 100644[m
[1mindex 0000000..b606cd5[m
[1m--- /dev/null[m
[1m+++ b/src/main/java/controller/list_mahasiswa.form[m
[36m@@ -0,0 +1,35 @@[m
[32m+[m[32m<?xml version="1.0" encoding="UTF-8" ?>[m
[32m+[m
[32m+[m[32m<Form version="1.3" maxVersion="1.9" type="org.netbeans.modules.form.forminfo.JFrameFormInfo">[m
[32m+[m[32m  <Properties>[m
[32m+[m[32m    <Property name="defaultCloseOperation" type="int" value="3"/>[m
[32m+[m[32m  </Properties>[m
[32m+[m[32m  <SyntheticProperties>[m
[32m+[m[32m    <SyntheticProperty name="formSizePolicy" type="int" value="1"/>[m
[32m+[m[32m    <SyntheticProperty name="generateCenter" type="boolean" value="false"/>[m
[32m+[m[32m  </SyntheticProperties>[m
[32m+[m[32m  <AuxValues>[m
[32m+[m[32m    <AuxValue name="FormSettings_autoResourcing" type="java.lang.Integer" value="0"/>[m
[32m+[m[32m    <AuxValue name="FormSettings_autoSetComponentName" type="java.lang.Boolean" value="false"/>[m
[32m+[m[32m    <AuxValue name="FormSettings_generateFQN" type="java.lang.Boolean" value="true"/>[m
[32m+[m[32m    <AuxValue name="FormSettings_generateMnemonicsCode" type="java.lang.Boolean" value="false"/>[m
[32m+[m[32m    <AuxValue name="FormSettings_i18nAutoMode" type="java.lang.Boolean" value="false"/>[m
[32m+[m[32m    <AuxValue name="FormSettings_layoutCodeTarget" type="java.lang.Integer" value="1"/>[m
[32m+[m[32m    <AuxValue name="FormSettings_listenerGenerationStyle" type="java.lang.Integer" value="3"/>[m
[32m+[m[32m    <AuxValue name="FormSettings_variablesLocal" type="java.lang.Boolean" value="false"/>[m
[32m+[m[32m    <AuxValue name="FormSettings_variablesModifier" type="java.lang.Integer" value="2"/>[m
[32m+[m[32m  </AuxValues>[m
[32m+[m
[32m+[m[32m  <Layout>[m
[32m+[m[32m    <DimensionLayout dim="0">[m
[32m+[m[32m      <Group type="103" groupAlignment="0" attributes="0">[m
[32m+[m[32m          <EmptySpace min="0" pref="400" max="32767" attributes="0"/>[m
[32m+[m[32m      </Group>[m
[32m+[m[32m    </DimensionLayout>[m
[32m+[m[32m    <DimensionLayout dim="1">[m
[32m+[m[32m      <Group type="103" groupAlignment="0" attributes="0">[m
[32m+[m[32m          <EmptySpace min="0" pref="300" max="32767" attributes="0"/>[m
[32m+[m[32m      </Group>[m
[32m+[m[32m    </DimensionLayout>[m
[32m+[m[32m  </Layout>[m
[32m+[m[32m</Form>[m
[1mdiff --git a/src/main/java/controller/list_mahasiswa.java b/src/main/java/controller/list_mahasiswa.java[m
[1mnew file mode 100644[m
[1mindex 0000000..5fa9f67[m
[1m--- /dev/null[m
[1m+++ b/src/main/java/controller/list_mahasiswa.java[m
[36m@@ -0,0 +1,45 @@[m
[32m+[m[32mpackage controller;[m
[32m+[m[32mpublic class list_mahasiswa extends javax.swing.JFrame {[m
[32m+[m[41m    [m
[32m+[m[32m    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(list_mahasiswa.class.getName());[m
[32m+[m[32m    public list_mahasiswa() {[m
[32m+[m[32m        initComponents();[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    @SuppressWarnings("unchecked")[m
[32m+[m[32m    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents[m
[32m+[m[32m    private void initComponents() {[m
[32m+[m
[32m+[m[32m        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);[m
[32m+[m
[32m+[m[32m        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());[m
[32m+[m[32m        getContentPane().setLayout(layout);[m
[32m+[m[32m        layout.setHorizontalGroup([m
[32m+[m[32m            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)[m
[32m+[m[32m            .addGap(0, 400, Short.MAX_VALUE)[m
[32m+[m[32m        );[m
[32m+[m[32m        layout.setVerticalGroup([m
[32m+[m[32m            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)[m
[32m+[m[32m            .addGap(0, 300, Short.MAX_VALUE)[m
[32m+[m[32m        );[m
[32m+[m
[32m+[m[32m        pack();[m
[32m+[m[32m    }// </editor-fold>//GEN-END:initComponents[m
[32m+[m
[32m+[m[32m    public static void main(String args[]) {[m
[32m+[m[32m        try {[m
[32m+[m[32m            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {[m
[32m+[m[32m                if ("Nimbus".equals(info.getName())) {[m
[32m+[m[32m                    javax.swing.UIManager.setLookAndFeel(info.getClassName());[m
[32m+[m[32m                    break;[m
[32m+[m[32m                }[m
[32m+[m[32m            }[m
[32m+[m[32m        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {[m
[32m+[m[32m            logger.log(java.util.logging.Level.SEVERE, null, ex);[m
[32m+[m[32m        }[m
[32m+[m[32m        java.awt.EventQueue.invokeLater(() -> new list_mahasiswa().setVisible(true));[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    // Variables declaration - do not modify//GEN-BEGIN:variables[m
[32m+[m[32m    // End of variables declaration//GEN-END:variables[m
[32m+[m[32m}[m
[1mdiff --git a/src/main/java/controller/mdifikasi_mahasiswa.form b/src/main/java/controller/mdifikasi_mahasiswa.form[m
[1mnew file mode 100644[m
[1mindex 0000000..b606cd5[m
[1m--- /dev/null[m
[1m+++ b/src/main/java/controller/mdifikasi_mahasiswa.form[m
[36m@@ -0,0 +1,35 @@[m
[32m+[m[32m<?xml version="1.0" encoding="UTF-8" ?>[m
[32m+[m
[32m+[m[32m<Form version="1.3" maxVersion="1.9" type="org.netbeans.modules.form.forminfo.JFrameFormInfo">[m
[32m+[m[32m  <Properties>[m
[32m+[m[32m    <Property name="defaultCloseOperation" type="int" value="3"/>[m
[32m+[m[32m  </Properties>[m
[32m+[m[32m  <SyntheticProperties>[m
[32m+[m[32m    <SyntheticProperty name="formSizePolicy" type="int" value="1"/>[m
[32m+[m[32m    <SyntheticProperty name="generateCenter" type="boolean" value="false"/>[m
[32m+[m[32m  </SyntheticProperties>[m
[32m+[m[32m  <AuxValues>[m
[32m+[m[32m    <AuxValue name="FormSettings_autoResourcing" type="java.lang.Integer" value="0"/>[m
[32m+[m[32m    <AuxValue name="FormSettings_autoSetComponentName" type="java.lang.Boolean" value="false"/>[m
[32m+[m[32m    <AuxValue name="FormSettings_generateFQN" type="java.lang.Boolean" value="true"/>[m
[32m+[m[32m    <AuxValue name="FormSettings_generateMnemonicsCode" type="java.lang.Boolean" value="false"/>[m
[32m+[m[32m    <AuxValue name="FormSettings_i18nAutoMode" type="java.lang.Boolean" value="false"/>[m
[32m+[m[32m    <AuxValue name="FormSettings_layoutCodeTarget" type="java.lang.Integer" value="1"/>[m
[32m+[m[32m    <AuxValue name="FormSettings_listenerGenerationStyle" type="java.lang.Integer" value="3"/>[m
[32m+[m[32m    <AuxValue name="FormSettings_variablesLocal" type="java.lang.Boolean" value="false"/>[m
[32m+[m[32m    <AuxValue name="FormSettings_variablesModifier" type="java.lang.Integer" value="2"/>[m
[32m+[m[32m  </AuxValues>[m
[32m+[m
[32m+[m[32m  <Layout>[m
[32m+[m[32m    <DimensionLayout dim="0">[m
[32m+[m[32m      <Group type="103" groupAlignment="0" attributes="0">[m
[32m+[m[32m          <EmptySpace min="0" pref="400" max="32767" attributes="0"/>[m
[32m+[m[32m      </Group>[m
[32m+[m[32m    </DimensionLayout>[m
[32m+[m[32m    <DimensionLayout dim="1">[m
[32m+[m[32m      <Group type="103" groupAlignment="0" attributes="0">[m
[32m+[m[32m          <EmptySpace min="0" pref="300" max="32767" attributes="0"/>[m
[32m+[m[32m      </Group>[m
[32m+[m[32m    </DimensionLayout>[m
[32m+[m[32m  </Layout>[m
[32m+[m[32m</Form>[m
[1mdiff --git a/src/main/java/controller/Laporan.java b/src/main/java/controller/mdifikasi_mahasiswa.java[m
[1msimilarity index 100%[m
[1mrename from src/main/java/controller/Laporan.java[m
[1mrename to src/main/java/controller/mdifikasi_mahasiswa.java[m
