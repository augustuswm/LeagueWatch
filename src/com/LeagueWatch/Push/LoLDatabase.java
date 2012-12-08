package com.LeagueWatch.Push;

import java.util.HashMap;
import com.LeagueWatch.R;

public class LoLDatabase {

	private int[] championIcons = new int[144];
	private String[] championNames = new String[144];
	private HashMap<String, String> services = new HashMap<String, String>();
	
	public LoLDatabase() {
		services.put("o", "Own3d");
		services.put("t", "Twitch");

		championIcons[0] = R.drawable.unknown;
		championIcons[1] = R.drawable.annie;
		championIcons[2] = R.drawable.olaf;
		championIcons[3] = R.drawable.galio;
		championIcons[4] = R.drawable.twistedfate;
		championIcons[5] = R.drawable.xinzhao;
		championIcons[6] = R.drawable.urgot;
		championIcons[7] = R.drawable.leblanc;
		championIcons[8] = R.drawable.vladimir;
		championIcons[9] = R.drawable.fiddlesticks;
		championIcons[10] = R.drawable.kayle;
		championIcons[11] = R.drawable.masteryi;
		championIcons[12] = R.drawable.alistar;
		championIcons[13] = R.drawable.ryze;
		championIcons[14] = R.drawable.sion;
		championIcons[15] = R.drawable.sivir;
		championIcons[16] = R.drawable.soraka;
		championIcons[17] = R.drawable.teemo;
		championIcons[18] = R.drawable.tristana;
		championIcons[19] = R.drawable.warwick;
		championIcons[20] = R.drawable.nunu;
		championIcons[21] = R.drawable.missfortune;
		championIcons[22] = R.drawable.ashe;
		championIcons[23] = R.drawable.tryndamere;
		championIcons[24] = R.drawable.jax;
		championIcons[25] = R.drawable.morgana;
		championIcons[26] = R.drawable.zilean;
		championIcons[27] = R.drawable.singed;
		championIcons[28] = R.drawable.evelynn;
		championIcons[29] = R.drawable.twitch;
		championIcons[30] = R.drawable.karthus;
		championIcons[31] = R.drawable.chogath;
		championIcons[32] = R.drawable.amumu;
		championIcons[33] = R.drawable.rammus;
		championIcons[34] = R.drawable.anivia;
		championIcons[35] = R.drawable.shaco;
		championIcons[36] = R.drawable.drmundo;
		championIcons[37] = R.drawable.sona;
		championIcons[38] = R.drawable.kassadin;
		championIcons[39] = R.drawable.irelia;
		championIcons[40] = R.drawable.janna;
		championIcons[41] = R.drawable.gangplank;
		championIcons[42] = R.drawable.corki;
		championIcons[43] = R.drawable.karma;
		championIcons[44] = R.drawable.taric;
		championIcons[45] = R.drawable.veigar;
		championIcons[48] = R.drawable.trundle;
		championIcons[50] = R.drawable.swain;
		championIcons[51] = R.drawable.caitlyn;
		championIcons[53] = R.drawable.blitzcrank;
		championIcons[54] = R.drawable.malphite;
		championIcons[55] = R.drawable.katarina;
		championIcons[56] = R.drawable.nocturne;
		championIcons[57] = R.drawable.maokai;
		championIcons[58] = R.drawable.renekton;
		championIcons[59] = R.drawable.jarvaniv;
		championIcons[61] = R.drawable.orianna;
		championIcons[62] = R.drawable.wukong;
		championIcons[63] = R.drawable.brand;
		championIcons[64] = R.drawable.leesin;
		championIcons[67] = R.drawable.vayne;
		championIcons[68] = R.drawable.rumble;
		championIcons[69] = R.drawable.cassiopeia;
		championIcons[72] = R.drawable.skarner;
		championIcons[74] = R.drawable.heimerdinger;
		championIcons[75] = R.drawable.nasus;
		championIcons[76] = R.drawable.nidalee;
		championIcons[77] = R.drawable.udyr;
		championIcons[78] = R.drawable.poppy;
		championIcons[79] = R.drawable.gragas;
		championIcons[80] = R.drawable.pantheon;
		championIcons[81] = R.drawable.ezreal;
		championIcons[82] = R.drawable.mordekaiser;
		championIcons[83] = R.drawable.yorick;
		championIcons[84] = R.drawable.akali;
		championIcons[85] = R.drawable.kennen;
		championIcons[86] = R.drawable.garen;
		championIcons[89] = R.drawable.leona;
		championIcons[90] = R.drawable.malzahar;
		championIcons[91] = R.drawable.talon;
		championIcons[92] = R.drawable.riven;
		championIcons[96] = R.drawable.kogmaw;
		championIcons[98] = R.drawable.shen;
		championIcons[99] = R.drawable.lux;
		championIcons[101] = R.drawable.xerath;
		championIcons[102] = R.drawable.shyvana;
		championIcons[103] = R.drawable.ahri;
		championIcons[104] = R.drawable.graves;
		championIcons[105] = R.drawable.fizz;
		championIcons[106] = R.drawable.volibear;
		championIcons[107] = R.drawable.rengar;
		championIcons[110] = R.drawable.varus;
		championIcons[111] = R.drawable.nautilus;
		championIcons[112] = R.drawable.viktor;
		championIcons[113] = R.drawable.sejuani;
		championIcons[114] = R.drawable.fiora;
		championIcons[115] = R.drawable.ziggs;
		championIcons[117] = R.drawable.lulu;
		championIcons[119] = R.drawable.draven;
		championIcons[120] = R.drawable.hecarim;
		championIcons[121] = R.drawable.khazix;
		championIcons[122] = R.drawable.darius;
		championIcons[126] = R.drawable.jayce;
		championIcons[131] = R.drawable.diana;
		championIcons[134] = R.drawable.syndra;
		championIcons[143] = R.drawable.zyra;
		
		championNames[0] = "unknown";
		championNames[1] = "Annie";
		championNames[2] = "Olaf";
		championNames[3] = "Galio";
		championNames[4] = "Twisted Fate";
		championNames[5] = "Xin Zhao";
		championNames[6] = "Urgot";
		championNames[7] = "LeBlanc";
		championNames[8] = "Vladimir";
		championNames[9] = "Fiddlesticks";
		championNames[10] = "Kayle";
		championNames[11] = "Master Yi";
		championNames[12] = "Alistar";
		championNames[13] = "Ryze";
		championNames[14] = "Sion";
		championNames[15] = "Sivir";
		championNames[16] = "Soraka";
		championNames[17] = "Teemo";
		championNames[18] = "Tristana";
		championNames[19] = "Warwick";
		championNames[20] = "Nunu";
		championNames[21] = "Miss Fortune";
		championNames[22] = "Ashe";
		championNames[23] = "Tryndamere";
		championNames[24] = "Jax";
		championNames[25] = "Morgana";
		championNames[26] = "Zilean";
		championNames[27] = "Singed";
		championNames[28] = "Evelynn";
		championNames[29] = "Twitch";
		championNames[30] = "Karthus";
		championNames[31] = "Cho'Gath";
		championNames[32] = "Amumu";
		championNames[33] = "Rammus";
		championNames[34] = "Anivia";
		championNames[35] = "Shaco";
		championNames[36] = "Dr. Mundo";
		championNames[37] = "Sona";
		championNames[38] = "Kassadin";
		championNames[39] = "Irelia";
		championNames[40] = "Janna";
		championNames[41] = "Gangplank";
		championNames[42] = "Corki";
		championNames[43] = "Karma";
		championNames[44] = "Taric";
		championNames[45] = "Veigar";
		championNames[48] = "Trundle";
		championNames[50] = "Swain";
		championNames[51] = "Caitlyn";
		championNames[53] = "Blitzcrank";
		championNames[54] = "Malphite";
		championNames[55] = "Katarina";
		championNames[56] = "Nocturne";
		championNames[57] = "Maokai";
		championNames[58] = "Renekton";
		championNames[59] = "Jarvan IV";
		championNames[61] = "Orianna";
		championNames[62] = "Wukong";
		championNames[63] = "Brand";
		championNames[64] = "Lee Sin";
		championNames[67] = "Vayne";
		championNames[68] = "Rumble";
		championNames[69] = "Cassiopeia";
		championNames[72] = "Skarner";
		championNames[74] = "Heimerdinger";
		championNames[75] = "Nasus";
		championNames[76] = "Nidalee";
		championNames[77] = "Udyr";
		championNames[78] = "Poppy";
		championNames[79] = "Gragas";
		championNames[80] = "Pantheon";
		championNames[81] = "Ezreal";
		championNames[82] = "Mordekaiser";
		championNames[83] = "Yorick";
		championNames[84] = "Akali";
		championNames[85] = "Kennen";
		championNames[86] = "Garen";
		championNames[89] = "Leona";
		championNames[90] = "Malzahar";
		championNames[91] = "Talon";
		championNames[92] = "Riven";
		championNames[96] = "Kog'Maw";
		championNames[98] = "Shen";
		championNames[99] = "Lux";
		championNames[101] = "Xerath";
		championNames[102] = "Shyvana";
		championNames[103] = "Ahri";
		championNames[104] = "Graves";
		championNames[105] = "Fizz";
		championNames[106] = "Volibear";
		championNames[107] = "Rengar";
		championNames[110] = "Varus";
		championNames[111] = "Nautilus";
		championNames[112] = "Viktor";
		championNames[113] = "Sejuani";
		championNames[114] = "Fiora";
		championNames[115] = "Ziggs";
		championNames[117] = "Lulu";
		championNames[119] = "Draven";
		championNames[120] = "Hecarim";
		championNames[121] = "Kha'Zix";
		championNames[122] = "Darius";
		championNames[126] = "Jayce";
		championNames[131] = "Diana";
		championNames[134] = "Syndra";
		championNames[143] = "Zyra";
	}

	public int getIcon(int id) {
		return championIcons[id];
	}
	
	public int[] getIcons() {
		return championIcons;
	}
	
	public String getName(int id) {
		return championNames[id];
	}
	
	public String[] getNames() {
		return championNames;
	}
	
	public HashMap getServices() {
		return services;
	}
	
	public String getService(String id) {
		return services.get(id);
	}	
	
}
