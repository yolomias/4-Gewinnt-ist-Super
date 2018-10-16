package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Vier_GUI
{
    private JPanel dasPanel;
    private JButton button11;
    private JButton button10;
    private JButton button12;
    private JButton button20;
    private JButton button21;
    private JButton buttonNeustart;
    private JButton button30;
    private JButton button40;
    private JButton button50;
    private JButton button60;
    private JButton button70;
    private JButton button13;
    private JButton button14;
    private JButton button15;
    private JButton button16;
    private JButton button22;
    private JButton button23;
    private JButton button24;
    private JButton button25;
    private JButton button26;
    private JButton button31;
    private JButton button32;
    private JButton button33;
    private JButton button34;
    private JButton button35;
    private JButton button36;
    private JButton button41;
    private JButton button42;
    private JButton button43;
    private JButton button44;
    private JButton button45;
    private JButton button46;
    private JButton button51;
    private JButton button52;
    private JButton button53;
    private JButton button54;
    private JButton button55;
    private JButton button56;
    private JButton button62;
    private JButton button63;
    private JButton button64;
    private JButton button65;
    private JButton button66;
    private JButton button61;
    private JButton button71;
    private JButton button72;
    private JButton button73;
    private JButton button74;
    private JButton button75;
    private JButton button76;
    private JPanel fensterleiste;
    private JButton roterButton;
    private JButton gelberButton;
    private JButton gelb2Button;
    private JButton gruen2Button;
    private JButton rot2Button;
    private JButton gruenerButton;
    private JPanel seitenLeiste;
    private JLabel windowIcon;
    private JRadioButton appleButton;
    private JLabel appleIcon;
    private JRadioButton windowButton;
    private JRadioButton otherButton;
    private JButton infoButton;
    private JLabel platzHalter;
    private JButton buttonGegenKILeicht;
    private JButton buttonGegenKiSchwer;
    private static JFrame frame = new JFrame("4 Gewinnt ist 4 Gewinnt");

    private char[][] spielFeld;
    private boolean starter = true;
    private int gerade = 0;
    private byte zaehlerRot = 0;
    private byte zaehlerGelb = 0;
    private boolean gewinn = false;
    private int xx = 0;
    private int yy = 0;
    private String select = "apple";
    private boolean maximierer = false;
    private boolean kiLeicht = false;
    private boolean kischwer = false;
    private byte kiZaehler = 0;
    private volatile boolean threadRunning = false;

    public static void main (String[] args) throws InterruptedException
    {
        frame.setContentPane(new Vier_GUI().dasPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        frame.pack();
        frame.setVisible(true);
    }

    //Wenn ein Knopf auf den Spielfeld gedrückt wird, soll ein Stein gesetzt werden
    private void buttonPressed (int zahl)
    {
        if (starter)
        {
            for (byte b = 5; b >= 0; b--) {
                if (spielFeld[b][zahl] == 'E' && (gerade % 2) == 0)
                {
                    setzeRolloverIcons(false);
                    setzeIcon(b, zahl, true);
                    spielFeld[b][zahl] = 'R';
                    gerade += 1;
                    b = -1;
                    platzHalter.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldAnimated.gif")));
                    pruefeGewinn();
                }
                else if (spielFeld[b][zahl] == 'E' && (gerade % 2) != 0)
                {
                    setzeRolloverIcons(true);
                    setzeIcon(b, zahl, false);
                    spielFeld[b][zahl] = 'G';
                    gerade += 1;
                    b = -1;
                    platzHalter.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldAnimated.gif")));
                    pruefeGewinn();
                    if (kiLeicht && !gewinn)
                    {
                        for (int l = 0; l <= 8192; l++)
                        {
                            kiZugRand();
                            if ((gerade % 2) != 0) break;
                        }

                    }

                    if (kischwer && !gewinn) kiZugSchwer();
                }
                pruefeGewinn();
            }
        }
    }

    //Überprüfe ob ein Spieler gewonnen hat
    private void pruefeGewinn ()
    {
        //prüfe horizontale
        if (!gewinn)
        {
            for (byte b = 0; b <= 5; b++)
            {
                zaehlerRot = 0;
                zaehlerGelb = 0;
                for (byte c = 0; c <= 6; c++)
                {

                    if (spielFeld[b][c] == 'R')
                    {
                        zaehlerGelb = 0;
                        zaehlerRot += 1;
                        if (zaehlerRot >= 4)
                        {
                            JOptionPane.showMessageDialog(frame, "Spieler Rot hat gewonnen!");
                            gewinn = true;
                            starter = false;
                            b = 6;
                            c = 7;
                        }
                    }
                    else if (spielFeld[b][c] == 'G')
                    {
                        zaehlerRot = 0;
                        zaehlerGelb += 1;
                        if (zaehlerGelb >= 4)
                        {
                            JOptionPane.showMessageDialog(frame, "Spieler Gelb hat gewonnen!");
                            gewinn = true;
                            starter = false;
                            b = 6;
                            c = 7;
                        }
                    }
                    else if ( spielFeld[b][c] == 'E')
                    {
                        zaehlerRot = 0;
                        zaehlerGelb = 0;
                    }
                }
            }
        }
        //prüfe vertikale
        if (!gewinn)
        {
            for (byte b = 0; b <= 6; b++)
            {
                zaehlerRot = 0;
                zaehlerGelb = 0;

                for (byte c = 0; c <= 5; c++)
                {
                    if (spielFeld[c][b] == 'R')
                    {
                        zaehlerGelb = 0;
                        zaehlerRot += 1;
                        if (zaehlerRot >= 4)
                        {
                            JOptionPane.showMessageDialog(frame, "Spieler Rot hat gewonnen!");
                            gewinn = true;
                            starter = false;
                            b = 7;
                            c = 6;
                        }
                    }
                    else if (spielFeld[c][b] == 'G')
                    {
                        zaehlerRot = 0;
                        zaehlerGelb += 1;
                        if (zaehlerGelb >= 4)
                        {
                            JOptionPane.showMessageDialog(frame, "Spieler Gelb hat gewonnen!");
                            gewinn = true;
                            starter = false;
                            b = 7;
                            c = 6;
                        }
                    }
                    else if ( spielFeld[c][b] == 'E')
                    {
                        zaehlerRot = 0;
                        zaehlerGelb = 0;
                    }
                }
            }
        }
        //prüfe diagonale nach rechts
        byte e;
        byte f;

        if (!gewinn)
        {
            for (byte b = 5; b >= 0; b--)
            {
                for (byte c = 0; c <= 6; c++)
                {
                    e = c;
                    f = b;

                    zaehlerRot = 0;
                    zaehlerGelb = 0;

                    while (e <= 6 && f >= 0)
                    {
                        if (spielFeld[f][e] == 'R')
                        {
                            zaehlerGelb = 0;
                            zaehlerRot += 1;
                            if (zaehlerRot >= 4)
                            {
                                JOptionPane.showMessageDialog(frame, "Spieler Rot hat gewonnen!");
                                gewinn = true;
                                starter = false;
                                b = -1;
                                c = 7;
                                e = 7;
                                f = -1;
                            }
                        }
                        else if (spielFeld[f][e] == 'G')
                        {
                            zaehlerRot = 0;
                            zaehlerGelb += 1;
                            if (zaehlerGelb >= 4)
                            {
                                JOptionPane.showMessageDialog(frame, "Spieler Gelb hat gewonnen!");
                                gewinn = true;
                                starter = false;
                                b = -1;
                                c = 7;
                                e = 7;
                                f = -1;
                            }
                        }
                        else if ( spielFeld[f][e] == 'E')
                        {
                            zaehlerRot = 0;
                            zaehlerGelb = 0;
                        }
                        e += 1;
                        f -= 1;
                    }
                }
            }
        }
        //prüfe diagonale nach links
        if (!gewinn)
        {
            for (byte b = 5; b >= 0; b--)
            {
                for (byte c = 6; c >= 0; c--)
                {
                    e = c;
                    f = b;

                    zaehlerRot = 0;
                    zaehlerGelb = 0;

                    while (e >= 0 && f >= 0)
                    {
                        if (spielFeld[f][e] == 'R')
                        {
                            zaehlerGelb = 0;
                            zaehlerRot += 1;
                            if (zaehlerRot >= 4)
                            {
                                JOptionPane.showMessageDialog(frame, "Spieler Rot hat gewonnen!");
                                gewinn = true;
                                starter = false;
                                b = -1;
                                c = 7;
                                e = 7;
                                f = -1;
                            }
                        }
                        else if (spielFeld[f][e] == 'G')
                        {
                            zaehlerRot = 0;
                            zaehlerGelb += 1;
                            if (zaehlerGelb >= 4)
                            {
                                JOptionPane.showMessageDialog(frame, "Spieler Gelb hat gewonnen!");
                                gewinn = true;
                                starter = false;
                                b = -1;
                                c = 7;
                                e = 7;
                                f = -1;
                            }
                        }
                        else if ( spielFeld[f][e] == 'E')
                        {
                            zaehlerRot = 0;
                            zaehlerGelb = 0;
                        }
                        e -= 1;
                        f -= 1;
                    }
                }
            }
        }
    }

    //Setze das Spielfeld auf den Ursprungszustand zurück
    private void resetSpiel()
    {
        spielFeld = new char[7][7];

       // aktiviereRolloverIcons();

        platzHalter.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldAnimated.gif")));
        gerade = 0;
        starter = true;
        gewinn = false;
        for (byte b = 0; b <= 6; b ++)
        {
            for (byte c = 0; c <= 6; c++)
            {
                if (b >= 6)
                {
                    spielFeld[6][c] = 'B'; //B wie Blockiert laal
                }
                else spielFeld[b][c] = 'E'; // E wie Empty lool
            }
        }
        setzeRolloverIcons(true);
        leereIcons();
    }

    //Starte ein Spiel gegen einen Menschlichen Spieler
    private void neuStart()
    {
        resetSpiel();
        kiLeicht = false;
        kischwer = false;
    }

    //Starte ein Spiel gegen die Leichte KI
    private void starteKILeicht ()
    {
        resetSpiel();
        kiLeicht = true;
        kischwer = false;
        kiZugRand();
    }

    //Mache einen zufälligen Zug
    private void kiZugRand ()
    {
        Random rand = new Random();
        int random = rand.nextInt(7);
        buttonPressed(random);
    }

    //Starte ein Spiel gegen die schwere KI
    private void starteKISchwer ()
    {
        resetSpiel();
        kiLeicht = false;
        kischwer = true;
        buttonPressed(3);
        kiZaehler = 1;
    }

    //Macht einen zufälligen Zug für die schwere KI
    private void kiRandZugHard ()
    {
        int i = 0;
        while (i <= 8192)
        {
            kiZugRand();
            i++;
            if ((gerade % 2) != 0 ) i = 8193; //Bricht die schleife ab
        }
    }

    //Mache einen vordefinierten Zug für die schwere KI
    private void kiZugSchwer ()
    {
        if (kischwer && !blockiereZug())
        {
            if (kiZaehler == 1)
            {
                if (spielFeld[5][1] == 'E' && spielFeld[5][2] == 'E' && spielFeld[5][4] == 'E' && spielFeld[5][5] == 'E') buttonPressed(2);
                else if (spielFeld[5][3] == 'R' && spielFeld[4][3] == 'E') buttonPressed(3);
                else kiRandZugHard();
            }
            else if (kiZaehler == 2)
            {
                if (spielFeld[5][4] == 'E') buttonPressed(4);
                else if (spielFeld[5][3] == 'R' && spielFeld[4][3] == 'R' && spielFeld[3][3] == 'E') buttonPressed(3);
                else kiRandZugHard();
            }
            else if (kiZaehler == 3)
            {
                if (spielFeld[5][2] == 'R' && spielFeld[5][3] == 'R' && spielFeld[5][4] == 'R' && spielFeld[5][5] == 'E') buttonPressed(5);
                else if (spielFeld[5][2] == 'R' && spielFeld[5][3] == 'R' && spielFeld[5][4] == 'R' && spielFeld[5][1] == 'E') buttonPressed(1);
                else if (spielFeld[3][3] == 'R' && spielFeld[4][3] == 'R' && spielFeld[5][3] == 'R' &&spielFeld[2][3] == 'E') buttonPressed(3);
                else kiRandZugHard();
            }
            else kiRandZugHard();
            kiZaehler++;
        }
    }

    //blockiert oder gewinnt den Zug
    private boolean blockiereZug ()
    {
        byte k = 0;
        byte l = 0;

        //prüfe diagonal nach rechts-hoch rot
        for (byte e = 5; e >= 3; e--)
        {
            for (byte f = 0; f <= 3; f++)
            {
                k = e;
                l = f;
                zaehlerGelb = 0;
                zaehlerRot = 0;

                while (k >= 0 && l <= 6)
                {
                    if (spielFeld[k][l] == 'R') zaehlerRot++;
                    else if (spielFeld[k][l] == 'G') zaehlerRot = 0;
                    else if (spielFeld[k][l] == 'E' && spielFeld[k + 1][l] != 'E' && (zaehlerRot >= 3))
                        {
                            buttonPressed(l);
                            return true;
                        }
                    k--;
                    l++;
                }
            }
        }

        //prüfe diagonal nach links-hoch rot
        for (byte f = 5; f >= 3; f--)
        {
            for (byte g = 6; g >= 3; g--)
            {
                k = f;
                l = g;
                zaehlerGelb = 0;
                zaehlerRot = 0;

                while (k >= 0 && l >= 0)
                {
                    if (spielFeld[k][l] == 'R') zaehlerRot++;
                    else if (spielFeld[k][l] == 'G') zaehlerRot = 0;
                    else if (spielFeld[k][l] == 'E' && spielFeld[k + 1][l] != 'E' && zaehlerRot >= 3)
                    {
                        buttonPressed(l);
                        return true;
                    }
                    k--;
                    l--;
                }
            }
        }


        //prüfe horizontale nach rechts rot
        for (byte b = 5; b >= 0; b--)
        {
            k = b;
            zaehlerGelb = 0;
            zaehlerRot = 0;

            for (byte c = 0; c <= 6; c++)
            {
                if (zaehlerRot >= 3 && spielFeld[b][c] == 'E' && spielFeld[k + 1][c] != 'E')
                {
                    buttonPressed(c);
                    b = -1;
                    c = 7;
                    return true;
                }
                else if (spielFeld[b][c] == 'R')
                {
                    zaehlerGelb = 0;
                    zaehlerRot++;
                }
                else if (spielFeld[b][c] == 'G') zaehlerRot = 0;
            }
        }

        //prüfe horizontale nach links rot
        for (byte b = 5; b >= 0; b--)
        {
            k = b;
            zaehlerGelb = 0;
            zaehlerRot = 0;

            for (byte c = 6; c >= 0; c--)
            {
                if (zaehlerRot >= 3 && spielFeld[b][c] == 'E' && spielFeld[k + 1][c] != 'E')
                {
                    buttonPressed(c);
                    b = -1;
                    c = 7;
                    return true;
                } else if (spielFeld[b][c] == 'R')
                {
                    zaehlerGelb = 0;
                    zaehlerRot++;
                }
                else if (spielFeld[b][c] == 'G') zaehlerRot = 0;
            }
        }

        //prüfe vertikale rot
        for (byte b = 0; b <= 6; b++)
        {
            zaehlerRot = 0;
            zaehlerGelb = 0;

            for (byte c = 5; c >= 0; c--)
            {
                if (zaehlerRot >= 3 && spielFeld[c][b] == 'E')
                {
                    buttonPressed(b);
                    b = -1;
                    c = 7;
                    return true;
                }
                else if (spielFeld[c][b] == 'R')
                {
                    zaehlerGelb = 0;
                    zaehlerRot++;
                }
                else if (spielFeld[c][b] == 'G') zaehlerRot = 0;
            }
        }

        //prüfe diagonal nach rechts-hoch gelb
        for (byte h = 5; h >= 3; h--)
        {
            for (byte i = 0; i <= 3; i++)
            {
                k = h;
                l = i;
                zaehlerGelb = 0;
                zaehlerRot = 0;

                while (k >= 0 && l <= 6)
                {
                    if (spielFeld[k][l] == 'G') zaehlerGelb++;
                    else if (spielFeld[k][l] == 'R') zaehlerGelb = 0;
                    else if (spielFeld[k][l] == 'E' && spielFeld[k + 1][l] != 'E' && zaehlerGelb >= 3)
                    {
                        buttonPressed(l);
                        return true;
                    }
                    k--;
                    l++;
                }
            }
        }

        //prüfe diagonal nach links-hoch gelb
        for (byte j = 5; j >= 3; j--)
        {
            for (byte h = 6; h >= 3; h--)
            {
                k = j;
                l = h;
                zaehlerGelb = 0;
                zaehlerRot = 0;

                while (k >= 0 && l >= 0)
                {
                    if (spielFeld[k][l] == 'G') zaehlerGelb++;
                    else if (spielFeld[k][l] == 'R') zaehlerGelb = 0;
                    else if (spielFeld[k][l] == 'E' && spielFeld[k + 1][l] != 'E' && zaehlerGelb >= 3)
                    {
                        buttonPressed(l);
                        return true;
                    }
                    k--;
                    l--;
                }
            }
        }

        //prüfe vertikale gelb
        for (byte b = 0; b <= 6; b++)
        {
            zaehlerRot = 0;
            zaehlerGelb = 0;

            for (byte c = 5; c >= 0; c--)
            {
                if (zaehlerGelb >= 3 && spielFeld[c][b] == 'E')
                {
                    buttonPressed(b);
                    b = -1;
                    c = 7;
                    return true;
                }
                else if (spielFeld[c][b] == 'G')
                {
                    zaehlerRot = 0;
                    zaehlerGelb++;
                }
                else if (spielFeld[c][b] == 'R') zaehlerGelb = 0;
            }
        }

        //prüfe horizontale nach rechts gelb
        for (byte b = 5; b >= 0; b--)
        {
            k = b;
            zaehlerGelb = 0;
            zaehlerRot = 0;

            for (byte c = 0; c <= 6; c++)
            {
                if (zaehlerGelb >= 2 && spielFeld[b][c] == 'E' && spielFeld[k + 1][c] != 'E')
                {
                    buttonPressed(c);
                    b = -1;
                    c = 7;
                    return true;
                }
                else if (spielFeld[b][c] == 'G')
                {
                    zaehlerRot = 0;
                    zaehlerGelb++;
                }
                else if (spielFeld[b][c] == 'R') zaehlerGelb = 0;
            }
        }

        //prüfe horizontale nach links gelb
        for (byte b = 5; b >= 0; b--)
        {
            k = b;
            zaehlerGelb = 0;
            zaehlerRot = 0;

            for (byte c = 6; c >= 0; c--)
            {
                if (zaehlerGelb >= 2 && spielFeld[b][c] == 'E' && spielFeld[k + 1][c] != 'E')
                {
                    buttonPressed(c);
                    b = -1;
                    c = 7;
                    return true;
                }
                else if (spielFeld[b][c] == 'G')
                {
                    zaehlerRot = 0;
                    zaehlerGelb++;
                }
                else if (spielFeld[b][c] == 'R') zaehlerGelb = 0;
            }
        }

        return false;
    }

    //Setze ein Leeres Feld Icon auf alle Spielfelder
    private void leereIcons ()
    {
        button11.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button12.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button13.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button14.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button15.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button16.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));

        button21.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button22.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button23.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button24.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button25.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button26.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));

        button31.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button32.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button33.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button34.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button35.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button36.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));

        button41.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button42.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button43.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button44.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button45.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button46.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));

        button51.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button52.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button53.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button54.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button55.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button56.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));

        button61.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button62.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button63.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button64.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button65.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button66.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));

        button71.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button72.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button73.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button74.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button75.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
        button76.setIcon(new ImageIcon(Class.class.getResource("/img/leeresFeld.gif")));
    }

    //Setze ein Icon auf das Fel,d was belegt wurde und schalte den Rollover auf diesem Feld aus
    private void setzeIcon(byte x, int y,boolean z)
    {
        //rot
        if (z)
        {
            if (x == 0 && y == 0)
            {
                button11.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button11.setRolloverIcon(null);
            }
            else if (x == 1 && y == 0)
            {
                button12.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button12.setRolloverIcon(null);
            }
            else if (x == 2 && y == 0)
            {
                button13.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button13.setRolloverIcon(null);
            }
            else if (x == 3 && y == 0)
            {
                button14.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button14.setRolloverIcon(null);
            }
            else if (x == 4 && y == 0)
            {
                button15.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button15.setRolloverIcon(null);
            }
            else if (x == 5 && y == 0)
            {
                button16.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button16.setRolloverIcon(null);
            }

            else if (x == 0 && y == 1)
            {
                button21.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button21.setRolloverIcon(null);
            }
            else if (x == 1 && y == 1)
            {
                button22.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button22.setRolloverIcon(null);
            }
            else if (x == 2 && y == 1)
            {
                button23.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button23.setRolloverIcon(null);
            }
            else if (x == 3 && y == 1)
            {
                button24.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button24.setRolloverIcon(null);
            }
            else if (x == 4 && y == 1)
            {
                button25.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button25.setRolloverIcon(null);
            }
            else if (x == 5 && y == 1)
            {
                button26.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button26.setRolloverIcon(null);
            }

            else if (x == 0 && y == 2)
            {
                button31.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button31.setRolloverIcon(null);
            }
            else if (x == 1 && y == 2)
            {
                button32.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button32.setRolloverIcon(null);
            }
            else if (x == 2 && y == 2)
            {
                button33.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button33.setRolloverIcon(null);
            }
            else if (x == 3 && y == 2)
            {
                button34.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button34.setRolloverIcon(null);
            }
            else if (x == 4 && y == 2)
            {
                button35.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button35.setRolloverIcon(null);
            }
            else if (x == 5 && y == 2)
            {
                button36.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button36.setRolloverIcon(null);
            }

            else if (x == 0 && y == 3)
            {
                button41.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button41.setRolloverIcon(null);
            }
            else if (x == 1 && y == 3)
            {
                button42.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button42.setRolloverIcon(null);
            }
            else if (x == 2 && y == 3)
            {
                button43.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button43.setRolloverIcon(null);
            }
            else if (x == 3 && y == 3)
            {
                button44.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button44.setRolloverIcon(null);
            }
            else if (x == 4 && y == 3)
            {
                button45.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button45.setRolloverIcon(null);
            }
            else if (x == 5 && y == 3)
            {
                button46.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button46.setRolloverIcon(null);
            }

            else if (x == 0 && y == 4)
            {
                button51.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button51.setRolloverIcon(null);
            }
            else if (x == 1 && y == 4)
            {
                button52.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button52.setRolloverIcon(null);
            }
            else if (x == 2 && y == 4)
            {
                button53.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button53.setRolloverIcon(null);
            }
            else if (x == 3 && y == 4)
            {
                button54.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button54.setRolloverIcon(null);
            }
            else if (x == 4 && y == 4)
            {
                button55.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button55.setRolloverIcon(null);
            }
            else if (x == 5 && y == 4)
            {
                button56.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button56.setRolloverIcon(null);
            }

            else if (x == 0 && y == 5)
            {
                button61.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button61.setRolloverIcon(null);
            }
            else if (x == 1 && y == 5)
            {
                button62.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button62.setRolloverIcon(null);
            }
            else if (x == 2 && y == 5)
            {
                button63.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button63.setRolloverIcon(null);
            }
            else if (x == 3 && y == 5)
            {
                button64.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button64.setRolloverIcon(null);
            }
            else if (x == 4 && y == 5)
            {
                button65.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button65.setRolloverIcon(null);
            }
            else if (x == 5 && y == 5)
            {
                button66.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button66.setRolloverIcon(null);
            }

            else if (x == 0 && y == 6)
            {
                button71.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button71.setRolloverIcon(null);
            }
            else if (x == 1 && y == 6)
            {
                button72.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button72.setRolloverIcon(null);
            }
            else if (x == 2 && y == 6)
            {
                button73.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button73.setRolloverIcon(null);
            }
            else if (x == 3 && y == 6)
            {
                button74.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button74.setRolloverIcon(null);
            }
            else if (x == 4 && y == 6)
            {
                button75.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button75.setRolloverIcon(null);
            }
            else if (x == 5 && y == 6)
            {
                button76.setIcon(new ImageIcon(Class.class.getResource("/img/rotesFeld.gif")));
                button76.setRolloverIcon(null);
            }
        }
        //gelb
        else if (!z)
        {
            if (x == 0 && y == 0) {
                button11.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button11.setRolloverIcon(null);
            }
            else if (x == 1 && y == 0)
            {
                button12.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button12.setRolloverIcon(null);
            }
            else if (x == 2 && y == 0)
            {
                button13.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button13.setRolloverIcon(null);
            }
            else if (x == 3 && y == 0)
            {
                button14.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button14.setRolloverIcon(null);
            }
            else if (x == 4 && y == 0)
            {
                button15.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button15.setRolloverIcon(null);
            }
            else if (x == 5 && y == 0)
            {
                button16.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button16.setRolloverIcon(null);
            }

            else if (x == 0 && y == 1)
            {
                button21.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button21.setRolloverIcon(null);
            }
            else if (x == 1 && y == 1)
            {
                button22.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button22.setRolloverIcon(null);
            }
            else if (x == 2 && y == 1)
            {
                button23.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button23.setRolloverIcon(null);
            }
            else if (x == 3 && y == 1)
            {
                button24.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button24.setRolloverIcon(null);
            }
            else if (x == 4 && y == 1)
            {
                button25.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button25.setRolloverIcon(null);
            }
            else if (x == 5 && y == 1)
            {
                button26.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button26.setRolloverIcon(null);
            }

            else if (x == 0 && y == 2)
            {
                button31.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button31.setRolloverIcon(null);
            }
            else if (x == 1 && y == 2)
            {
                button32.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button32.setRolloverIcon(null);
            }
            else if (x == 2 && y == 2)
            {
                button33.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button33.setRolloverIcon(null);
            }
            else if (x == 3 && y == 2)
            {
                button34.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button34.setRolloverIcon(null);
            }
            else if (x == 4 && y == 2)
            {
                button35.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button35.setRolloverIcon(null);
            }
            else if (x == 5 && y == 2)
            {
                button36.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button36.setRolloverIcon(null);
            }

            else if (x == 0 && y == 3)
            {
                button41.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button41.setRolloverIcon(null);
            }
            else if (x == 1 && y == 3)
            {
                button42.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button42.setRolloverIcon(null);
            }
            else if (x == 2 && y == 3)
            {
                button43.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button43.setRolloverIcon(null);
            }
            else if (x == 3 && y == 3)
            {
                button44.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button44.setRolloverIcon(null);
            }
            else if (x == 4 && y == 3)
            {
                button45.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button45.setRolloverIcon(null);
            }
            else if (x == 5 && y == 3)
            {
                button46.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button46.setRolloverIcon(null);
            }

            else if (x == 0 && y == 4)
            {
                button51.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button51.setRolloverIcon(null);
            }
            else if (x == 1 && y == 4)
            {
                button52.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button52.setRolloverIcon(null);
            }
            else if (x == 2 && y == 4)
            {
                button53.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button53.setRolloverIcon(null);
            }
            else if (x == 3 && y == 4)
            {
                button54.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button54.setRolloverIcon(null);
            }
            else if (x == 4 && y == 4)
            {
                button55.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button55.setRolloverIcon(null);
            }
            else if (x == 5 && y == 4)
            {
                button56.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button56.setRolloverIcon(null);
            }

            else if (x == 0 && y == 5)
            {
                button61.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button61.setRolloverIcon(null);
            }
            else if (x == 1 && y == 5)
            {
                button62.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button62.setRolloverIcon(null);
            }
            else if (x == 2 && y == 5)
            {
                button63.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button63.setRolloverIcon(null);
            }
            else if (x == 3 && y == 5)
            {
                button64.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button64.setRolloverIcon(null);
            }
            else if (x == 4 && y == 5)
            {
                button65.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button65.setRolloverIcon(null);
            }
            else if (x == 5 && y == 5)
            {
                button66.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button66.setRolloverIcon(null);
            }

            else if (x == 0 && y == 6)
            {
                button71.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button71.setRolloverIcon(null);
            }
            else if (x == 1 && y == 6)
            {
                button72.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button72.setRolloverIcon(null);
            }
            else if (x == 2 && y == 6)
            {
                button73.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button73.setRolloverIcon(null);
            }
            else if (x == 3 && y == 6)
            {
                button74.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button74.setRolloverIcon(null);
            }
            else if (x == 4 && y == 6)
            {
                button75.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button75.setRolloverIcon(null);
            }
            else if (x == 5 && y == 6)
            {
                button76.setIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeld.gif")));
                button76.setRolloverIcon(null);
            }
        }
    }

    //Setze die Rollover Icons
    private void setzeRolloverIcons (boolean b)
    {
        //rot
        if (b)
        {
            if(spielFeld[0][0] == 'E') button11.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[1][0] == 'E') button12.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[2][0] == 'E') button13.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[3][0] == 'E') button14.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[4][0] == 'E') button15.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[5][0] == 'E') button16.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));

            if(spielFeld[0][1] == 'E') button21.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[1][1] == 'E') button22.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[2][1] == 'E') button23.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[3][1] == 'E') button24.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[4][1] == 'E') button25.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[5][1] == 'E') button26.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));

            if(spielFeld[0][2] == 'E') button31.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[1][2] == 'E') button32.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[2][2] == 'E') button33.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[3][2] == 'E') button34.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[4][2] == 'E') button35.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[5][2] == 'E') button36.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));

            if(spielFeld[0][3] == 'E') button41.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[1][3] == 'E') button42.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[2][3] == 'E') button43.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[3][3] == 'E') button44.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[4][3] == 'E') button45.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[5][3] == 'E') button46.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));

            if(spielFeld[0][4] == 'E') button51.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[1][4] == 'E') button52.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[2][4] == 'E') button53.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[3][4] == 'E') button54.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[4][4] == 'E') button55.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[5][4] == 'E') button56.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));

            if(spielFeld[0][5] == 'E') button61.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[1][5] == 'E') button62.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[2][5] == 'E') button63.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[3][5] == 'E') button64.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[4][5] == 'E') button65.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[5][5] == 'E') button66.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));

            if(spielFeld[0][6] == 'E') button71.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[1][6] == 'E') button72.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[2][6] == 'E') button73.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[3][6] == 'E') button74.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[4][6] == 'E') button75.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));
            if(spielFeld[5][6] == 'E') button76.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rotesFeldRollover.gif")));

        }
        //gelb
        else if (!b)
        {
            if(spielFeld[0][0] == 'E') button11.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[1][0] == 'E') button12.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[2][0] == 'E') button13.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[3][0] == 'E') button14.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[4][0] == 'E') button15.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[5][0] == 'E') button16.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));

            if(spielFeld[0][1] == 'E') button21.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[1][1] == 'E') button22.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[2][1] == 'E') button23.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[3][1] == 'E') button24.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[4][1] == 'E') button25.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[5][1] == 'E') button26.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));

            if(spielFeld[0][2] == 'E') button31.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[1][2] == 'E') button32.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[2][2] == 'E') button33.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[3][2] == 'E') button34.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[4][2] == 'E') button35.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[5][2] == 'E') button36.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));

            if(spielFeld[0][3] == 'E') button41.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[1][3] == 'E') button42.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[2][3] == 'E') button43.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[3][3] == 'E') button44.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[4][3] == 'E') button45.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[5][3] == 'E') button46.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));

            if(spielFeld[0][4] == 'E') button51.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[1][4] == 'E') button52.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[2][4] == 'E') button53.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[3][4] == 'E') button54.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[4][4] == 'E') button55.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[5][4] == 'E') button56.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));

            if(spielFeld[0][5] == 'E') button61.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[1][5] == 'E') button62.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[2][5] == 'E') button63.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[3][5] == 'E') button64.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[4][5] == 'E') button65.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[5][5] == 'E') button66.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));

            if(spielFeld[0][6] == 'E') button71.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[1][6] == 'E') button72.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[2][6] == 'E') button73.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[3][6] == 'E') button74.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[4][6] == 'E') button75.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
            if(spielFeld[5][6] == 'E') button76.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelbesFeldRollover.gif")));
        }
    }

    //Selektiert das zuletzt verwendete Design
    private void selektor ()
    {
        switch (select)
        {
            case "apple":
                appleButton.setSelected(true);
                break;
            case "window":
                windowButton.setSelected(true);
                break;
            case "other":
                otherButton.setSelected(true);
                break;
        }
    }

    private void tastenAbfrage (char buchstabe)
    {
        if      (buchstabe == '1') buttonPressed(0);
        else if (buchstabe == '2') buttonPressed(1);
        else if (buchstabe == '3') buttonPressed(2);
        else if (buchstabe == '4') buttonPressed(3);
        else if (buchstabe == '5') buttonPressed(4);
        else if (buchstabe == '6') buttonPressed(5);
        else if (buchstabe == '7') buttonPressed(6);
    }

    private Vier_GUI ()
    {
        neuStart();

        buttonNeustart.addActionListener(e -> neuStart());

        buttonGegenKILeicht.addActionListener(e -> starteKILeicht());

        buttonGegenKiSchwer.addActionListener(e -> starteKISchwer());

        button10.addActionListener(e -> buttonPressed(0));

        button11.addActionListener(e -> buttonPressed(0));

        button12.addActionListener(e -> buttonPressed(0));

        button13.addActionListener(e -> buttonPressed(0));

        button14.addActionListener(e -> buttonPressed(0));

        button15.addActionListener(e -> buttonPressed(0));

        button16.addActionListener(e -> buttonPressed(0));

        button20.addActionListener(e -> buttonPressed(1));

        button21.addActionListener(e -> buttonPressed(1));

        button22.addActionListener(e -> buttonPressed(1));

        button23.addActionListener(e -> buttonPressed(1));

        button24.addActionListener(e -> buttonPressed(1));

        button25.addActionListener(e -> buttonPressed(1));

        button26.addActionListener(e -> buttonPressed(1));

        button30.addActionListener(e -> buttonPressed(2));

        button31.addActionListener(e -> buttonPressed(2));

        button32.addActionListener(e -> buttonPressed(2));

        button33.addActionListener(e -> buttonPressed(2));

        button34.addActionListener(e -> buttonPressed(2));

        button35.addActionListener(e -> buttonPressed(2));

        button36.addActionListener(e -> buttonPressed(2));

        button40.addActionListener(e -> buttonPressed(3));

        button41.addActionListener(e -> buttonPressed(3));

        button42.addActionListener(e -> buttonPressed(3));

        button43.addActionListener(e -> buttonPressed(3));

        button44.addActionListener(e -> buttonPressed(3));

        button45.addActionListener(e -> buttonPressed(3));

        button46.addActionListener(e -> buttonPressed(3));

        button50.addActionListener(e -> buttonPressed(4));

        button51.addActionListener(e -> buttonPressed(4));

        button52.addActionListener(e -> buttonPressed(4));

        button53.addActionListener(e -> buttonPressed(4));

        button54.addActionListener(e -> buttonPressed(4));

        button55.addActionListener(e -> buttonPressed(4));

        button56.addActionListener(e -> buttonPressed(4));

        button60.addActionListener(e -> buttonPressed(5));

        button61.addActionListener(e -> buttonPressed(5));

        button62.addActionListener(e -> buttonPressed(5));

        button63.addActionListener(e -> buttonPressed(5));

        button64.addActionListener(e -> buttonPressed(5));

        button65.addActionListener(e -> buttonPressed(5));

        button66.addActionListener(e -> buttonPressed(5));

        button70.addActionListener(e -> buttonPressed(6));

        button71.addActionListener(e -> buttonPressed(6));

        button72.addActionListener(e -> buttonPressed(6));

        button73.addActionListener(e -> buttonPressed(6));

        button74.addActionListener(e -> buttonPressed(6));

        button75.addActionListener(e -> buttonPressed(6));

        button76.addActionListener(e -> buttonPressed(6));

        platzHalter.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if ((gerade % 2) == 0) JOptionPane.showMessageDialog(frame,"Spieler Rot ist am Zug.");
                else JOptionPane.showMessageDialog(frame, "Spieler Gelb ist am Zug.");
            }
        });

        button10.addFocusListener(new FocusListener()
        {
            @Override
            public void focusLost(FocusEvent e)
            {
                AbstractButton btn = (AbstractButton) e.getSource();
                btn.setIcon(new ImageIcon(Class.class.getResource("/img/arrow-down.png")));
            }

            @Override
            public void focusGained(FocusEvent e)
            {
                AbstractButton btn = (AbstractButton) e.getSource();
                btn.setIcon(new ImageIcon(Class.class.getResource("/img/arrow-down-rollover.png")));
            }
        });

        button10.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                tastenAbfrage(e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

        });

        button20.addFocusListener(new FocusListener()
        {
            @Override
            public void focusLost(FocusEvent e)
            {
                AbstractButton btn = (AbstractButton) e.getSource();
                btn.setIcon(new ImageIcon(Class.class.getResource("/img/arrow-down.png")));
            }

            @Override
            public void focusGained(FocusEvent e)
            {
                AbstractButton btn = (AbstractButton) e.getSource();
                btn.setIcon(new ImageIcon(Class.class.getResource("/img/arrow-down-rollover.png")));
            }
        });

        button20.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                tastenAbfrage(e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

        });

        button30.addFocusListener(new FocusListener()
        {
            @Override
            public void focusLost(FocusEvent e)
            {
                AbstractButton btn = (AbstractButton) e.getSource();
                btn.setIcon(new ImageIcon(Class.class.getResource("/img/arrow-down.png")));
            }

            @Override
            public void focusGained(FocusEvent e)
            {
                AbstractButton btn = (AbstractButton) e.getSource();
                btn.setIcon(new ImageIcon(Class.class.getResource("/img/arrow-down-rollover.png")));
            }
        });

        button30.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                tastenAbfrage(e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

        });

        button40.addFocusListener(new FocusListener()
        {
            @Override
            public void focusLost(FocusEvent e)
            {
                AbstractButton btn = (AbstractButton) e.getSource();
                btn.setIcon(new ImageIcon(Class.class.getResource("/img/arrow-down.png")));
            }

            @Override
            public void focusGained(FocusEvent e)
            {
                AbstractButton btn = (AbstractButton) e.getSource();
                btn.setIcon(new ImageIcon(Class.class.getResource("/img/arrow-down-rollover.png")));
            }
        });

        button40.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                tastenAbfrage(e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

        });

        button50.addFocusListener(new FocusListener()
        {
            @Override
            public void focusLost(FocusEvent e)
            {
                AbstractButton btn = (AbstractButton) e.getSource();
                btn.setIcon(new ImageIcon(Class.class.getResource("/img/arrow-down.png")));
            }

            @Override
            public void focusGained(FocusEvent e)
            {
                AbstractButton btn = (AbstractButton) e.getSource();
                btn.setIcon(new ImageIcon(Class.class.getResource("/img/arrow-down-rollover.png")));
            }
        });

        button50.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                tastenAbfrage(e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

        });

        button60.addFocusListener(new FocusListener()
        {
            @Override
            public void focusLost(FocusEvent e)
            {
                AbstractButton btn = (AbstractButton) e.getSource();
                btn.setIcon(new ImageIcon(Class.class.getResource("/img/arrow-down.png")));
            }

            @Override
            public void focusGained(FocusEvent e)
            {
                AbstractButton btn = (AbstractButton) e.getSource();
                btn.setIcon(new ImageIcon(Class.class.getResource("/img/arrow-down-rollover.png")));
            }
        });

        button60.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                tastenAbfrage(e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

        });

        button70.addFocusListener(new FocusListener()
        {
            @Override
            public void focusLost(FocusEvent e)
            {
                AbstractButton btn = (AbstractButton) e.getSource();
                btn.setIcon(new ImageIcon(Class.class.getResource("/img/arrow-down.png")));
            }

            @Override
            public void focusGained(FocusEvent e)
            {
                AbstractButton btn = (AbstractButton) e.getSource();
                btn.setIcon(new ImageIcon(Class.class.getResource("/img/arrow-down-rollover.png")));
            }
        });

        button70.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                tastenAbfrage(e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

        });

        //----Die weiteren Buttons sind nur für die Navigationstasten und für die Funktion des Programms nicht relevant -------------
        //Minimiert das Programmfenster
        gruenerButton.addActionListener(e -> frame.setState(Frame.ICONIFIED));

        //Maximiert das Programmfenster
        gelberButton.addActionListener(e ->
        {
            //Aktivieren wenn keine Maximierung gewünscht ist
            JOptionPane.showMessageDialog(frame, "Die Maximierung wurde deaktiviert!");
        });

        //Beendet das Programm
        roterButton.addActionListener(e -> System.exit(0));

        //Die Buttons machen das gleiche wie die oberen, sind aber im Apple Look nicht sichtbar
        gruen2Button.addActionListener(e -> frame.setState(Frame.ICONIFIED));

        gelb2Button.addActionListener(e ->
        {
            //Aktivieren wenn keine Maximierung gewünscht ist
            JOptionPane.showMessageDialog(frame, "Die Maximierung wurde deaktiviert!");
        });

        rot2Button.addActionListener(e -> System.exit(0));

        fensterleiste.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                xx = e.getX();
                yy = e.getY();
            }
        });

        fensterleiste.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                frame.setLocation(frame.getLocation().x+e.getX()-xx, frame.getLocation().y+e.getY()-yy);
            }
        });

        ButtonGroup groupie = new ButtonGroup();
        groupie.add(appleButton);
        groupie.add(windowButton);
        groupie.add(otherButton);

        //Ändert das Design in Apple Optik
        appleButton.addActionListener(e ->
        {
            frame.dispose();
            frame.setUndecorated(true);

            fensterleiste.setBackground(Color.decode("#C2C2C2"));
            gruenerButton.setIcon(new ImageIcon(Class.class.getResource("/img/gruen.png")));
            gruenerButton.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gruen_mouseover.png")));
            gruenerButton.setMargin(new Insets(2, 5, 2, 5));
            gruenerButton.setVisible(true);
            gruen2Button.setVisible(false);

            gelberButton.setIcon(new ImageIcon(Class.class.getResource("/img/gelb.png")));
            gelberButton.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/gelb_mouseover.png")));
            gelberButton.setMargin(new Insets(2, 5, 2, 5));
            gelberButton.setVisible(true);
            gelb2Button.setVisible(false);

            roterButton.setIcon(new ImageIcon(Class.class.getResource("/img/rot.png")));
            roterButton.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/rot_mouseover.png")));
            roterButton.setMargin(new Insets(2, 5, 2, 5));
            roterButton.setVisible(true);
            rot2Button.setVisible(false);

            fensterleiste.setVisible(true);
            frame.pack();
            frame.setVisible(true);
            select = "apple";
        });

        //Ändert das Design in Windows Optik
        windowButton.addActionListener(e ->
        {
            frame.dispose();
            frame.setUndecorated(true);

            fensterleiste.setBackground(Color.decode("#0178d8"));
            gruen2Button.setIcon(new ImageIcon(Class.class.getResource("/img/line.png")));
            gruen2Button.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/line_rollover.png")));
            gruen2Button.setMargin(new Insets(0, 0, 0, 0));
            gruenerButton.setVisible(false);
            gruen2Button.setVisible(true);

            gelb2Button.setMargin(new Insets(0, 0, 0, 0));
            gelberButton.setVisible(false);
            if (maximierer)
            {
                gelb2Button.setIcon(new ImageIcon(Class.class.getResource("/img/square_maximized.png")));
                gelb2Button.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/square_maximized_rollover.png")));
            }
            else
            {
                gelb2Button.setIcon(new ImageIcon(Class.class.getResource("/img/square.png")));
                gelb2Button.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/square_rollover.png")));
            }
            gelb2Button.setVisible(true);

            rot2Button.setIcon(new ImageIcon(Class.class.getResource("/img/cross.png")));
            rot2Button.setRolloverIcon(new ImageIcon(Class.class.getResource("/img/cross_rollover.png")));
            rot2Button.setMargin(new Insets(0, 0, 0, 0));
            roterButton.setVisible(false);
            rot2Button.setVisible(true);

            fensterleiste.setVisible(true);
            frame.pack();
            frame.setVisible(true);
            select = "window";
        });

        //Ändert das Design in die Standardoptik des Betriebssystems
        otherButton.addActionListener(e ->
        {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (frame, " Wollen Sie sich wirklich das " +
                    "Standard Design des Systems antun? \n Besonders einige Linux Distributionen wie Ubuntu oder Elementary \n" +
                    " haben ein furchtbar grottiges Design, welches keinen Wert auf eine \n" +
                    " einfache Bedienung legt, obwohl es das bei anderen Betriebssystemen \n schon vor 23 Jahren gegeben hat.","Achtung!",dialogButton);
            if ((dialogResult == JOptionPane.YES_OPTION))
            {
                fensterleiste.setVisible(false);
                frame.dispose();
                frame.setUndecorated(false);
                frame.pack();
                frame.setVisible(true);
                select = "other";
            } else selektor();
        });

        //Gibt eine kleine Info über die Funktionen der Buttons darüber
        infoButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, " Über diese Seitenleiste" +
                " kann die Fensterdekoration eingestellt\n werden, wählen Sie den Look aus, der Ihnen am Besten gefällt!"));
    }
}