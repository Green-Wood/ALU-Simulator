public class FloatNumber extends ALU {

    public FloatNumber(String n1, String n2) {
        super(n1, n2);
    }

    protected String toDecimal(String bin) {
        String binaryExponent = bin.substring(1, 9);
        String binarySignificant = bin.substring(9, 32);
        if (isAllZero(binaryExponent) && isAllZero(binarySignificant)) {
            return "0";
        } else if (isAllOne(binaryExponent) && isAllZero(binarySignificant)) {
            if (bin.charAt(0) == '0') return "plus infinity";
            else return "minus infinity";
        } else if (isAllOne(binaryExponent) && !isAllZero(binarySignificant)) {
            return "NaN";
        } else if (isAllZero(binaryExponent) && !isAllZero(binarySignificant)){
            return readBinaryDenormalizd(bin);
        } else {
            return readBinaryNormalized(bin);
        }
    }

    private String readBinaryNormalized(String bin) {
        String binaryExponent = bin.substring(1, 9);
        String binarySignificant = bin.substring(9, 32);
        int decimalExponent = readBinaryExponent(binaryExponent);
        float decimalSignificant = readBinarySignificant(binarySignificant, true);
        float decimalValue = decimalSignificant * (float) Math.pow(2, decimalExponent);
        if (bin.charAt(0) == '1') decimalValue *= -1;
        return String.valueOf(decimalValue);
    }

    private String readBinaryDenormalizd(String bin) {
        String binarySignificant = bin.substring(9, 32);
        float decimalSignificant = readBinarySignificant(binarySignificant, false);
        float decimalValue = decimalSignificant * (float) Math.pow(2, -126);     // 非规格数直接乘以2的-126次方
        if (bin.charAt(0) == '1') decimalValue *= -1;
        return String.valueOf(decimalValue);
    }

    private int readBinaryExponent(String binaryExponent) {
        int decimalvalue = 0;
        for (int i = 0; i < binaryExponent.length(); i++) {
            decimalvalue  = decimalvalue << 1;
            if (binaryExponent.charAt(i) == '1') decimalvalue += 1;
        }
        decimalvalue -= 127;
        return decimalvalue;
    }

    private float readBinarySignificant(String binarySignificant, boolean isNormalized) {
        float decimalValue = 0;
        if (isNormalized) {
            decimalValue += 1;
        }
        double fractionValue = 0;
        for (int i = binarySignificant.length() - 1; i >= 0; i--) {
            fractionValue  *= 0.5;
            if (binarySignificant.charAt(i) == '1') fractionValue += 0.5;
        }
        decimalValue += fractionValue;
        return decimalValue;
    }

    private boolean isAllOne(String bin) {
        for (int i = 0; i < bin.length(); i++) {
            if (bin.charAt(i) != '1') return false;
        }
        return true;
    }

    private boolean isAllZero(String bin) {
        for (int i = 0; i < bin.length(); i++) {
            if (bin.charAt(i) != '0') return false;
        }
        return true;
    }

    protected String toBinary(String n) {
        if (n.equals("NaN")) {
            return "01111111100000000000000000000001";
        } else if (n.equals("plus infinity")) {
            return "01111111100000000000000000000000";
        } else if (n.equals("minus infinity")) {
            return "11111111100000000000000000000000";
        } else {
            return toBinaryWithActualValue(n);
        }
    }

    private String toBinaryWithActualValue(String n) {
        String hexString = Float.toHexString(Float.parseFloat(n));
        String sign = hexString.substring(0, 1);
        String beforePoint = hexString.split("[.]")[0];
        char isNormalized = beforePoint.charAt(beforePoint.length()-1);
        String hexValue = hexString.split("[.]")[1];
        String hexSignificant = hexValue.split("p")[0];
        String decimalExponent = hexValue.split("p")[1];
        if (isNormalized == '0') {            // 非规格化数存储时，要将指数存为-127
            decimalExponent = "-127";
        }
        String binaryExponent = toBinaryExponent(decimalExponent);
        String binarySignificant = toBinarySignificant(hexSignificant);
        if (sign.equals("-")) sign = "1";
        else sign = "0";
        return sign + binaryExponent + binarySignificant;
    }

    /**
     *
     * @param decimalExponent 十进制的指数
     * @return 返回一个8位长度的二进制指数
     */
    private String toBinaryExponent(String decimalExponent) {
        int decimalValue = Integer.parseInt(decimalExponent) + 127;
        String binaryExponent = Integer.toBinaryString(decimalValue);
        while (binaryExponent.length() != 8){
            binaryExponent = "0" + binaryExponent;
        }
        return binaryExponent;
    }

    /**
     *
     * @param hexExponent 十六进制的尾数
     * @return 返回一个23位长度的二进制尾数
     */
    private String toBinarySignificant(String hexExponent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hexExponent.length(); i++) {
            String currBin = getBinaryThroughHex(hexExponent.substring(i, i+1));
            sb.append(currBin);
        }
        while (sb.length() != 24) {
            sb.append("0000");
        }
        return sb.deleteCharAt(23).toString();
    }

    /**
     *
     * @param hex 一个十六进制数
     * @return  返回一个长度为4位的二进制数
     */
    private String getBinaryThroughHex(String hex) {
        int decimalValue = Integer.valueOf(hex, 16);
        String bin = Integer.toBinaryString(decimalValue);
        while (bin.length() != 4) {
            bin = '0' + bin;
        }
        return bin;
    }

    protected String add(String s1, String s2) {
        return null;
    }

    protected String sub(String s1, String s2) {
        if (s2.charAt(0) == '1') s2 = "0" + s2.substring(1);
        else s2 = "1" + s2.substring(1);
        return add(s1, s2);
    }

    protected String multi(String s1, String s2) {
        return null;
    }

    protected void division(String s1, String s2) {

    }
}
