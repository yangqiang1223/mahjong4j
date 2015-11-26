package org.mahjong4j;

import org.mahjong4j.hands.MahjongHands;
import org.mahjong4j.yaku.normals.MahjongYakuEnum;
import org.mahjong4j.yaku.yakuman.MahjongYakumanEnum;
import org.mahjong4j.yaku.yakuman.YakumanResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO:役満をまとめて判定する
 * TODO:通常役をまとめて判定
 *
 * @author yu1ro
 *         和了判定に関するクラスです。
 *         役の判定は別のクラスで行うがここから呼び出します
 */
public class Mahjong {
    /*
     * 付いた役のリストを保持する用の変数。 役満の場合は普通の役は見ない
     */
    public ArrayList<MahjongYakumanEnum> yakuman = new ArrayList<MahjongYakumanEnum>(1);
    public ArrayList<MahjongYakuEnum> normalYaku = new ArrayList<MahjongYakuEnum>(0);

    public String manName;//倍満 とか 跳満とかを入れる

    List<YakumanResolver> yakumanResolverList;


    public Mahjong(MahjongHands hands) {
        yakumanResolverList = Mahjong4jYakuConfig.getYakumanResolverList(hands);
    }

    public void calcYakuman() {
        for (YakumanResolver yakumanResolver : yakumanResolverList) {
            if (yakumanResolver.isMatch()) {
                yakuman.add(yakumanResolver.getYakuman());
            }
        }
    }

    public void calcNormalYaku() {

    }

    public int calcPoint() {

        if (yakuman.size() != 0) {
            switch (yakuman.size()) {
                case 1:
                    manName = "役満";
                    break;
                case 2:
                    manName = "ダブル役満";
                    break;
                case 3:
                    manName = "トリプル役満";
                    break;
            }
            return yakuman.size() * 32000;
        } else if (normalYaku.size() != 0) {
            int han = 0;
            for (MahjongYakuEnum aNormalYaku : normalYaku) {
                han += aNormalYaku.getHan();
            }

            switch (han) {
                case 1:
                    return 1000;
                case 2:
                    return 2000;
                case 3:
                    return 4000;
                case 4:
                case 5:
                    manName = "満貫";
                    return 8000;
                case 6:
                case 7:
                    manName = "跳満";
                    return 12000;
                case 8:
                case 9:
                case 10:
                    manName = "倍満";
                    return 16000;
                case 11:
                case 12:
                    manName = "三倍満";
                    return 24000;
                default:
                    if (han > 13) {
                        manName = "役満";
                        return 32000;
                    }
            }
        }
        return 0;
    }

    public void calcChitoiYakuman() {

    }

    public void calcChitoiYaku() {

    }
}
