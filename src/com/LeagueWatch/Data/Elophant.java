package com.LeagueWatch.Data;

import org.json.JSONArray;
import org.json.JSONObject;

import com.LeagueWatch.R;

public class Elophant extends FetchStream {
	
	public int[] championIcons = new int[144];

	public Elophant(String url) {
		super(url);

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
		//championIcons[119] = R.drawable.draven;
		championIcons[119] = R.drawable.unknown;
		championIcons[120] = R.drawable.hecarim;
		//championIcons[122] = R.drawable.darius;
		championIcons[122] = R.drawable.unknown;
		championIcons[126] = R.drawable.jayce;
		//championIcons[131] = R.drawable.diana;
		championIcons[131] = R.drawable.unknown;
		//championIcons[134] = R.drawable.syndra;
		championIcons[134] = R.drawable.unknown;
		//championIcons[143] = R.drawable.zyra;
		championIcons[143] = R.drawable.unknown;
		
		//Log.d("Stream", url);
		
	}
	
	public int fetchChampion(String streamerName) {
		
		try {
			String rawJSON = getJSON();
			JSONObject result = new JSONObject(rawJSON);
			if (result.has("game")) {
				result = result.getJSONObject("game");
				JSONArray jsonArray = result.getJSONArray("playerChampionSelections");
				
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					
					if (jsonObject.getString("summonerInternalName").equalsIgnoreCase(streamerName.replace(" ", ""))) {
						//Log.d("Stream", Integer.toString(championIcons[jsonObject.getInt("championId")]));
						return championIcons[jsonObject.getInt("championId")];
					}
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return R.drawable.unknown;
	}

}
