package comp1110.ass2;

/**
 * This class provides the text interface for the Warring States game
 */
public class WarringStatesGame {

    /**
     * Determine whether a card placement is well-formed according to the following:
     * - it consists of exactly three characters
     * - the first character is in the range a..g (kingdom) or is z (Zhang Yi)
     * - the second character is numeric, and is a valid character number for that kingdom (9 for Zhang Yi)
     * - the third character is in the range A .. Z or 0..9 (location)
     *
     * @param cardPlacement A string describing a card placement
     * @return true if the card placement is well-formed
     */
    static boolean isCardPlacementWellFormed(String cardPlacement) {
        // FIXME Task 2: determine whether a card placement is well-formed

        int cardLength = cardPlacement.length();

        if(cardLength == 3)
            if((cardPlacement.charAt(0) >= 'a' && cardPlacement.charAt(0) <= 'g') || (cardPlacement.charAt(0) == 'z'))
                if((cardPlacement.charAt(2) >= 'A' && cardPlacement.charAt(2) <= 'Z') || (cardPlacement.charAt(2) >= '0' && cardPlacement.charAt(2) <= '9'))
                    switch (cardPlacement.charAt(0))
                    {
                        case 'a':
                            if((cardPlacement.charAt(1) >= '0' && cardPlacement.charAt(1) <= '7'))
                                return true;
                            else break;
                        case 'b':
                            if((cardPlacement.charAt(1) >= '0' && cardPlacement.charAt(1) <= '6'))
                                return true;
                            else break;
                        case 'c':
                            if((cardPlacement.charAt(1) >= '0' && cardPlacement.charAt(1) <= '5'))
                                return true;
                            else break;
                        case 'd':
                            if((cardPlacement.charAt(1) >= '0' && cardPlacement.charAt(1) <= '4'))
                                return true;
                            else break;
                        case 'e':
                            if((cardPlacement.charAt(1) >= '0' && cardPlacement.charAt(1) <= '3'))
                                return true;
                            else break;
                        case 'f':
                            if((cardPlacement.charAt(1) >= '0' && cardPlacement.charAt(1) <= '2'))
                                return true;
                            else break;
                        case 'g':
                            if((cardPlacement.charAt(1) >= '0' && cardPlacement.charAt(1) <= '1'))
                                return true;
                            else break;
                        case 'z':
                            if(cardPlacement.charAt(1) == '9')
                                return true;
                            else break;
                        default: break;
                    }
        return false;
    }

    /**
     * Determine whether a placement string is well-formed:
     * - it consists of exactly N three-character card placements (where N = 1 .. 36);
     * - each card placement is well-formed
     * - no card appears more than once in the placement
     * - no location contains more than one card
     *
     * @param placement A string describing a placement of one or more cards
     * @return true if the placement is well-formed
     */
    static boolean isPlacementWellFormed(String placement) {
        // FIXME Task 3: determine whether a placement is well-formed

        int i,j;
        char[] cardSegment = new char[3];
        String segment;

        if(placement == "" || placement==null)
            return false;

        System.out.println(placement);
        if(placement.length()%3 == 0) {
            for (i = 0; i < placement.length(); i += 3) {
                segment = placement.substring(i,i+3);
                if (!isCardPlacementWellFormed(segment))
                    return false;
            }
            for(i = 0; i < placement.length(); i += 3)
                for(j = i+3; j < placement.length(); j += 3) {
                    if(placement.charAt(i+2) == placement.charAt(j+2))
                        return false;
                    if(placement.charAt(i) == placement.charAt(j) && placement.charAt(i+1) == placement.charAt(j+1))
                        return false;
                }
            return true;
        }
        return false;
    }

    /**
     * Determine whether a given move is legal given a provided valid placement:
     * - the location char is in the range A .. Z or 0..9
     * - there is a card at the chosen location;
     * - the location is in the same row or column of the grid as Zhang Yi's current position; and
     * - drawing a line from Zhang Yi's current location through the card at the chosen location,
     *   there are no other cards along the line from the same kingdom as the chosen card
     *   that are further away from Zhang Yi.
     * @param placement    the current placement string
     * @param locationChar a location for Zhang Yi to move to
     * @return true if Zhang Yi may move to that location
     */

    //Reflect the location into a array and return the index
    static int locationTransfer(char locationChar){
        int position = -10;
        if(locationChar >= 'A' && locationChar <= 'Z')
            position = locationChar-'A';
        if(locationChar >= '0' && locationChar <= '9')
            position = locationChar-'0'+26;

        return position;
    }

    //judge whether x and y are in the same column
    static boolean isInSameColumn(int positionX,int positionY){
        int x,y;
        x = positionX/6;
        y = positionY/6;
        if(x == y)
            return true;
        else
            return false;
    }

    //judge whether x and y are in the same row
    static boolean isInSameRow(int positionX,int positionY){
        int x,y;
        x = positionX%6;
        y = positionY%6;
        if(x == y)
            return true;
        else
            return false;
    }

    //judge target is the furthest one in the certain direction
    static boolean isfurthest(String placement,char contry,int temp,int positionZhang,int positionCard){
        int positionOther;
        for(int k=0;k<placement.length();k+=3){
            positionOther = locationTransfer(placement.charAt(k+2));
            if(positionOther == temp && placement.charAt(k) == contry && positionOther!=positionCard)
                if(Math.abs(positionOther-positionZhang)>Math.abs(positionCard-positionZhang))
                    return false;
        }
        return true;
    }

    public static boolean isMoveLegal(String placement, char locationChar) {
        // FIXME Task 5: determine whether a given move is legal
        int i,j;
        char locationZhang=' ';
        int positionZhang,positionCard;
        char contry;

        if((locationChar >= 'A' && locationChar <= 'Z') || (locationChar >= '0' && locationChar <= '9')){
            for(i=0;i<placement.length();i+=3)
                if(placement.charAt(i+2) == locationChar) {
                    contry = placement.charAt(i);
                    for(j=0;j<placement.length();j+=3)
                        if(placement.charAt(j)=='z')
                            locationZhang = placement.charAt(j+2);

                    positionCard = locationTransfer(locationChar);
                    positionZhang = locationTransfer(locationZhang);

                    //consider that Yi Zhang and target are in the same column
                    if(isInSameColumn(positionCard,positionZhang)) {
                        if(positionZhang<positionCard){
                            for(j=positionZhang+1;j<positionZhang/6*6+6;j++)
                                if(!isfurthest(placement,contry,j,positionZhang,positionCard))
                                    return false;
                        }
                        else{
                            for(j=positionZhang/6*6;j<positionZhang;j++)
                                if(!isfurthest(placement,contry,j,positionZhang,positionCard))
                                    return false;
                        }
                        return true;
                    }

                    //consider that Yi Zhang and target are in the same row
                    if(isInSameRow(positionCard,positionZhang)){
                        if(positionZhang<positionCard) {
                            for (j = positionZhang; j < 36; j += 6)
                                if(!isfurthest(placement,contry,j,positionZhang,positionCard))
                                    return false;
                        }
                        else{
                            for (j = positionZhang%6; j < positionZhang; j += 6)
                                if(!isfurthest(placement,contry,j,positionZhang,positionCard))
                                    return false;
                        }
                        return true;
                    }

                }
        }
        return false;
    }

    /**
     * Determine whether a move sequence is valid.
     * To be valid, the move sequence must be comprised of 1..N location characters
     * showing the location to move for Zhang Yi, and each move must be valid
     * given the placement that would have resulted from the preceding sequence
     * of moves.
     *
     * @param setup        A placement string representing the board setup
     * @param moveSequence a string of location characters representing moves
     * @return True if the placement sequence is valid
     */
    static boolean isMoveSequenceValid(String setup, String moveSequence) {
        // FIXME Task 6: determine whether a placement sequence is valid

        int i,j,k,t=0;
        int start,end;
        int positionZhang,positionCard;
        char contry=' ';
        char l1=' ',l2=' ';
        String cut = "";
        String exchange;
        if(!isPlacementWellFormed(setup))
            return false;

        for(i=0;i<moveSequence.length();i++) {
            if (isMoveLegal(setup, moveSequence.charAt(i))) {
                t++;
                if (t == moveSequence.length())
                    return true;
                positionZhang = locationTransfer(setup.charAt(setup.indexOf('z') + 2));
                positionCard = locationTransfer(moveSequence.charAt(i));
                for(j=0;j<setup.length();j+=3)
                    if(setup.charAt(j+2) == moveSequence.charAt(i))
                        contry = setup.charAt(j);

                if (isInSameColumn(positionCard, positionZhang)) {
                    if (positionCard > positionZhang) {
                        start = positionZhang;
                        end = positionCard;
                    } else {
                        start = positionCard;
                        end = positionZhang;
                    }
                    for (j = start; j <= end; j++)
                        for (k = 0; k < setup.length(); k += 3)
                            if (setup.charAt(k) == contry && locationTransfer(setup.charAt(k + 2)) == j) {
                                cut = setup.substring(k + 3);
                                setup = setup.substring(0, k);
                                setup += cut;
                                k -= 3;
                            }
                }
                if (isInSameRow(positionCard, positionZhang)) {
                    if (positionCard > positionZhang) {
                        start = positionZhang;
                        end = positionCard;
                    } else {
                        start = positionCard;
                        end = positionZhang;
                    }
                    for (j = start; j <= end; j += 6)
                        for (k = 0; k < setup.length(); k += 3)
                            if (setup.charAt(k) == contry && locationTransfer(setup.charAt(k + 2)) == j) {
                                cut = setup.substring(k + 3);
                                setup = setup.substring(0, k);
                                setup += cut;
                                k -= 3;
                            }
                }

                l1 = setup.charAt(setup.indexOf('z') + 2);
                l2 = moveSequence.charAt(i);
                exchange = setup.replace(l1, l2);
                setup=exchange;
                System.out.println(setup+"\n");

            }
        }
        return false;
    }

    /**
     * Get the list of supporters for the chosen player, given the provided
     * setup and move sequence.
     * The list of supporters is a sequence of two-character card IDs, representing
     * the cards that the chosen player collected by moving Zhang Yi.
     *
     * @param setup        A placement string representing the board setup
     * @param moveSequence a string of location characters representing moves
     * @param numPlayers   the number of players in the game, must be in the range [2..4]
     * @param playerId     the player number for which to get the list of supporters, [0..(numPlayers-1)]
     * @return the list of supporters for the given player
     */
    public static String getSupporters(String setup, String moveSequence, int numPlayers, int playerId) {
        // FIXME Task 7: get the list of supporters for a given player after a sequence of moves
        if (numPlayers < 2 || numPlayers > 4)
            return null;
        String[] pieces = new String[setup.length()/3];
        int i = 0;
        int j = 0;
        while (i < pieces.length){
            pieces[i] = setup.substring(j,j+3);
            i++;
            j+=3;
        }
        String result = "";
        for (int k = 0; k < moveSequence.length(); k++) {
            if (isMoveSequenceValid(setup,moveSequence))
                result = result + moveOnce(setup,pieces,moveSequence.charAt(k));
            else
                result = null;
        }
        return result;
    }

    public static String moveOnce(String setup, String[] pieces, char move) {
        String zhangYi = "";
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i].charAt(0) == 'z')
                zhangYi = pieces[i];
        }
        String card = "";
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i].charAt(0) == move)
                card = pieces[i];
        }
        String temp;
        String result = "";
        int i = setup.indexOf(zhangYi.charAt(2));
        int j = setup.indexOf(card.charAt(2));
        int temp1;
        if (Math.abs(i - j) <= 5) {
            if (i > j) {
                temp = setup.substring(j - 2, i + 1);
                for (int k = 0; k < temp.length(); k += 3) {
                    if (temp.charAt(k) == card.charAt(0))
                        result = result + temp.substring(k, k + 2);
                }
            } else if (i < j) {
                temp = setup.substring(i - 2, j + 1);
                for (int k = 0; k < temp.length(); k += 3) {
                    if (temp.charAt(k) == card.charAt(0))
                        result = result + temp.substring(k, k + 2);
                }
            }
        }
        else {
            if (i > j) {
                for (int k = j - 2; k < i - 1; k+=18) {
                    if (setup.charAt(k) == card.charAt(0))
                        result = result + setup.substring(k,k+2);
                }
            }
            else if (i < j) {
                for (int k = i - 2; k < j - 1; k+=18) {
                    if (setup.charAt(k) == card.charAt(0))
                        result = result + setup.substring(k,k+2);
                }
            }
        }
        return result;
    }

    /**
     * Given a setup and move sequence, determine which player controls the flag of each kingdom
     * after all the moves in the sequence have been played.
     *
     * @param setup        A placement string representing the board setup
     * @param moveSequence a string of location characters representing a sequence of moves
     * @param numPlayers   the number of players in the game, must be in the range [2..4]
     * @return an array containing the player ID who controls each kingdom, where
     * - element 0 contains the player ID of the player who controls the flag of Qin
     * - element 1 contains the player ID of the player who controls the flag of Qi
     * - element 2 contains the player ID of the player who controls the flag of Chu
     * - element 3 contains the player ID of the player who controls the flag of Zhao
     * - element 4 contains the player ID of the player who controls the flag of Han
     * - element 5 contains the player ID of the player who controls the flag of Wei
     * - element 6 contains the player ID of the player who controls the flag of Yan
     * If no player controls a particular house, the element for that house will have the value -1.
     */
    public static int[] getFlags(String setup, String moveSequence, int numPlayers) {
        // FIXME Task 8: determine which player controls the flag of each kingdom after a given sequence of moves
        return null;
    }

    /**
     * Generate a legal move, given the provided placement string.
     * A move is valid if:
     * - the location char is in the range A .. Z or 0..9
     * - there is a card at the chosen location;
     * - the destination location is different to Zhang Yi's current location;
     * - the destination is in the same row or column of the grid as Zhang Yi's current location; and
     * - drawing a line from Zhang Yi's current location through the card at the chosen location,
     * there are no other cards along the line from the same kingdom as the chosen card
     * that are further away from Zhang Yi.
     * If there is no legal move available, return the null character '\0'.
     * @param placement the current placement string
     * @return a location character representing Zhang Yi's destination for the move
     */
    public static char generateMove(String placement) {
        // FIXME Task 10: generate a legal move
        return '\0';
    }
}