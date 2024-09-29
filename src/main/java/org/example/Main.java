//package org.example;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.EncodeHintType;
//import com.google.zxing.MultiFormatWriter;
//import com.google.zxing.NotFoundException;
//import com.google.zxing.WriterException;
//import com.google.zxing.client.j2se.MatrixToImageWriter;
//import com.google.zxing.common.BitMatrix;
//import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
//
//public class Main {
//    //static function that creates QR Code
//    public static void generateQRcode(String data, String path, String charset, Map map, int h, int w) throws WriterException, IOException {
////the BitMatrix class represents the 2D matrix of bits
////MultiFormatWriter is a factory class that finds the appropriate Writer subclass for the BarcodeFormat requested and encodes the barcode with the supplied contents.
//        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, w, h);
//        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
//    }
//
//    //main() method
//    public static void main(String args[]) throws WriterException, IOException, NotFoundException {
////data that we want to store in the QR code
//        String str = "hello world.";
////path where we want to get QR Code
//        String path = "D:\\Dev\\java\\Qr code gen\\qrcodes\\Quote.png";
////Encoding charset to be used
//        String charset = "UTF-8";
//        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
////generates QR code with Low level(L) error correction capability
//        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
////invoking the user-defined method that creates the QR code
//        generateQRcode(str, path, charset, hashMap, 200, 200);//increase or decrease height and width accodingly
////prints if the QR code is generated
//        System.out.println("QR Code created successfully.");
//    }
//}

package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class Main extends JFrame {
    private JTextField inputField;
    private JButton generateButton;
    private JLabel imageLabel;

    public Main() {
        setTitle("QR Code Generator");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputField = new JTextField();
        generateButton = new JButton("Generate QR Code");
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(generateButton, BorderLayout.EAST);

        // Image panel
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        // Add components to frame
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(imageLabel), BorderLayout.CENTER);

        // Add action listener to the generate button
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateQRCode();
            }
        });
    }

    private void generateQRCode() {
        String data = inputField.getText();
        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter text or URL");
            return;
        }

        try {
            String path = "qrcode.png";
            String charset = "UTF-8";
            Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

            generateQRcode(data, path, charset, hintMap, 200, 200);

            // Display the generated QR code
            ImageIcon icon = new ImageIcon(path);
            Image image = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(image));

            // Delete the temporary file
            new File(path).delete();

        } catch (WriterException | IOException ex) {
            JOptionPane.showMessageDialog(this, "Error generating QR code: " + ex.getMessage());
        }
    }

    public static void generateQRcode(String data, String path, String charset, Map<EncodeHintType, ErrorCorrectionLevel> map, int h, int w) throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, w, h);
        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}