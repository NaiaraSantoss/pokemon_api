package br.edu.iftm.poo.trabalho2;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

import br.edu.iftm.poo.trabalho2.interfaces.IApi;
import br.edu.iftm.poo.trabalho2.interfaces.IApiResourceCall;
import br.edu.iftm.poo.trabalho2.interfaces.IFactory;
import br.edu.iftm.poo.trabalho2.models.Ability;
import br.edu.iftm.poo.trabalho2.models.Move;
import br.edu.iftm.poo.trabalho2.models.NamedResource;
import br.edu.iftm.poo.trabalho2.models.NamedResourceList;
import br.edu.iftm.poo.trabalho2.models.Pokemon;
import br.edu.iftm.poo.trabalho2.models.PokemonSprites;
import br.edu.iftm.poo.trabalho2.models.PokemonStat;
import br.edu.iftm.poo.trabalho2.models.Species;
import br.edu.iftm.poo.trabalho2.models.Stat;
import br.edu.iftm.poo.trabalho2.models.Type;

import static java.util.Map.entry;

import java.io.IOException;
import java.util.HashMap;  

@SuppressWarnings("unused")
public class Api implements IApi {

	private static final String DEFAULT_URL = "https://pokeapi.co/api/v2/";	
	
	private String url;
	private IFactory factory; 
	
	private HttpTransport transport;
	private HttpRequestFactory requestFactory;
	
	// Tabela de instanciação por tipo
	private Map<Class<?>, IApiResourceCall<?>> typeTable = Map.ofEntries(
	    entry(Pokemon.class, new IApiResourceCall<Pokemon>() { public Pokemon call(int id) { return getPokemon(id); } }),
	    entry(Ability.class, new IApiResourceCall<Ability>() { public Ability call(int id) { return getAbility(id); } }),
	    entry(Move.class, 	 new IApiResourceCall<Move>()    { public Move    call(int id) { return getMove(id);    } }),
	    entry(Species.class, new IApiResourceCall<Species>() { public Species call(int id) { return getSpecies(id); } }),
	    entry(Stat.class, 	 new IApiResourceCall<Stat>() 	 { public Stat 	  call(int id) { return getStat(id); } }),
	    entry(Type.class, 	 new IApiResourceCall<Type>()    { public Type    call(int id) { return getType(id);    } })
	);
	
	// Buffer
	private Map<Integer, Pokemon> bufferedPokemons = new HashMap<Integer, Pokemon>();
	private Map<Integer, Ability> bufferedAbilities = new HashMap<Integer, Ability>();
	private Map<Integer, Move> bufferedMoves = new HashMap<Integer, Move>();
	private Map<Integer, Species> bufferedSpecies = new HashMap<Integer, Species>();
	private Map<Integer, Stat> bufferedStats = new HashMap<Integer, Stat>();
	private Map<Integer, Type> bufferedTypes = new HashMap<Integer, Type>();
	
	// Construtor
	public Api(IFactory factory) { 
		this(factory, DEFAULT_URL);
	}
	public Api(IFactory factory, String url) {		
		this.url = url;
		this.factory = factory;
	}
	
	// Getters
	public String getUrl() { return this.url; }
	public String getUrl(String append) { return this.url + append; }
	
	// HTTP	
	private HttpTransport transport() {
	    if(this.transport == null) 
	    	this.transport = new NetHttpTransport();
	    return this.transport;
	}
	 
	private HttpRequestFactory reqFactory() {
	    if(this.requestFactory == null) 
	    	this.requestFactory = this.transport().createRequestFactory();
	    return this.requestFactory;
	}
	
	private HttpResponse request(String url) throws IOException {
		return this.request(url, new HashMap<String, String>());
	}
	
	private HttpResponse request(String url, Map<String, String> cabecalhos) throws IOException {
		HttpRequest requisicao = this.reqFactory().buildGetRequest(new GenericUrl(url));
		HttpHeaders cabecalho = new HttpHeaders();
		
		for(String chave : cabecalhos.keySet()) 
			cabecalho.set(chave, cabecalhos.get(chave));
		
		requisicao.setHeaders(cabecalho);
		HttpResponse resposta = requisicao.execute();
		return resposta;
	}
	
	// Utilidades	
	private <T> NamedResource<T> getNamedResource(JSONObject object, Class<T> type) {
		NamedResource<T> namedResource = new NamedResource<T>(type);
		namedResource.setName(object.getString("name"));
		namedResource.setUrl(object.getString("url"));
		return namedResource;
	}
	
	private <T> NamedResourceList<T> getNamedResourceList(JSONArray array, Class<T> type) {
		NamedResourceList<T> namedResourceList = new NamedResourceList<T>(type);
		for(int i = 0; i < array.length(); i++) {
			JSONObject object = array.getJSONObject(i);
			namedResourceList.add(object.getString("name"), object.getString("url"));
		}
		return namedResourceList;
	}
	
	private <T> NamedResourceList<T> getNamedResourceList(String objectName, JSONArray array, Class<T> type) {
		NamedResourceList<T> namedResourceList = new NamedResourceList<T>(type);
		for(int i = 0; i < array.length(); i++) {
			JSONObject arrayObject = array.getJSONObject(i);
			JSONObject object = arrayObject.getJSONObject(objectName);
			namedResourceList.add(object.getString("name"), object.getString("url"));
		}
		return namedResourceList;
	}
	
	private Map<String, String> getNames(JSONArray array, String key) {
		Map<String, String> names = new HashMap<String, String>();
		for(int i = 0; i < array.length(); i++) {
			JSONObject object = array.getJSONObject(i);
			String name = object.getString(key);
			String language = object.getJSONObject("language").getString("name");
			if(!names.containsKey(language))
				names.put(language, name);
		}
		return names;
	}
	private Map<String, String> getNames(JSONArray array) {
		return this.getNames(array, "name");
	}
	
	private Map<String, String[]> getMultipleNames(JSONArray array, String key1, String key2) {
		Map<String, String[]> names = new HashMap<String, String[]>();
		for(int i = 0; i < array.length(); i++) {
			JSONObject object = array.getJSONObject(i);
			String name1 = object.getString(key1);
			String name2 = object.getString(key2);
			String language = object.getJSONObject("language").getString("name");
			if(!names.containsKey(language))
				names.put(language, new String[] { name1, name2 });
		}
		return names;
	}
	
	private boolean hasValidKey(JSONObject object, String key) {
		return object.has(key) && !object.isNull(key);
	}
	
	private String getValidString(JSONObject object, String key, String _default) {
		return this.hasValidKey(object, key) ? object.getString(key) : _default;
	}
	private String getValidString(JSONObject object, String key) {
		return this.getValidString(object, key, null);
	}
	
	private int getValidInt(JSONObject object, String key, int _default) {
		return this.hasValidKey(object, key) ? object.getInt(key) : _default;
	}
	private int getValidInt(JSONObject object, String key) {
		return this.getValidInt(object, key, -1);
	}
	
	private PokemonSprites getPokemonSprites(JSONObject object) {
		PokemonSprites sprites = new PokemonSprites();
		sprites.setFrontDefault(this.getValidString(object, "front_default"));
		sprites.setFrontShiny(this.getValidString(object, "front_shiny"));
		sprites.setFrontFemale(this.getValidString(object, "front_female"));
		sprites.setFrontShinyFemale(this.getValidString(object, "front_shiny_female"));
		sprites.setBackDefault(this.getValidString(object, "back_default"));
		sprites.setBackShiny(this.getValidString(object, "back_shiny"));
		sprites.setBackFemale(this.getValidString(object, "back_female"));
		sprites.setBackShinyFemale(this.getValidString(object, "back_shiny_female"));
		return sprites;
	}
	
	private PokemonStat[] getPokemonStats(JSONArray array) {
		PokemonStat[] stats = new PokemonStat[array.length()];
		for(int i = 0; i < array.length(); i++) {
			JSONObject object = array.getJSONObject(i);
			PokemonStat stat = new PokemonStat();
			stat.setBase(object.getInt("base_stat"));
			stat.setEffort(object.getInt("effort"));
			stat.setStat(this.getNamedResource(object.getJSONObject("stat"), Stat.class));
			stats[i] = stat;
		}
		return stats;
	}
	
	// API	
	@Override
	public Map<Class<?>, IApiResourceCall<?>> getTypeTable() { return this.typeTable; }
	
	@Override
	public NamedResourceList<Pokemon> enumeratePokemons() {
		try {
			String requestUrl = this.getUrl("pokemon/?limit=1000");
			HttpResponse response = this.request(requestUrl);
			JSONObject result = JsonParser.parseToObject(response);
			
			return this.getNamedResourceList(result.getJSONArray("results"), Pokemon.class);
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Pokemon getPokemon(int id) {
		if(this.bufferedPokemons.containsKey(id))
			return this.bufferedPokemons.get(id);
		
		try {
			String requestUrl = this.getUrl("pokemon/" + id + "/");
			HttpResponse response = this.request(requestUrl);
			JSONObject result = JsonParser.parseToObject(response);
			
			Pokemon pokemon = new Pokemon();
			pokemon.setId(id);
			pokemon.setName(result.getString("name"));
			pokemon.setBaseExperience(result.getInt("base_experience"));
			pokemon.setHeight(result.getInt("height"));
			pokemon.setDefault(result.getBoolean("is_default"));
			pokemon.setOrder(result.getInt("order"));
			pokemon.setWeight(result.getInt("weight"));
			pokemon.setAbilities(this.getNamedResourceList("ability", result.getJSONArray("abilities"), Ability.class));
			pokemon.setMoves(this.getNamedResourceList("move", result.getJSONArray("moves"), Move.class));
			pokemon.setSprites(this.getPokemonSprites(result.getJSONObject("sprites")));
			pokemon.setSpecies(this.getNamedResource(result.getJSONObject("species"), Species.class));
			pokemon.setStats(this.getPokemonStats(result.getJSONArray("stats")));
			pokemon.setTypes(this.getNamedResourceList("type", result.getJSONArray("types"), Type.class));
			
			this.bufferedPokemons.put(id, pokemon);
			return pokemon;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Ability getAbility(int id) {
		if(this.bufferedAbilities.containsKey(id))
			return this.bufferedAbilities.get(id);
		
		try {
			String requestUrl = this.getUrl("ability/" + id + "/");
			HttpResponse response = this.request(requestUrl);
			JSONObject result = JsonParser.parseToObject(response);
			
			Ability ability = new Ability();
			ability.setId(id);
			ability.setName(result.getString("name"));
			ability.setMainSeries(result.getBoolean("is_main_series"));
			ability.setNames(this.getNames(result.getJSONArray("names")));
			ability.setEffectEntries(this.getMultipleNames(result.getJSONArray("effect_entries"), "effect", "short_effect"));
			ability.setFlavorTextEntries(this.getNames(result.getJSONArray("flavor_text_entries"), "flavor_text"));
			ability.setPokemons(this.getNamedResourceList("pokemon", result.getJSONArray("pokemon"), Pokemon.class));
			
			this.bufferedAbilities.put(id, ability);
			return ability;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Move getMove(int id) {
		if(this.bufferedMoves.containsKey(id))
			return this.bufferedMoves.get(id);
		
		try {
			String requestUrl = this.getUrl("move/" + id + "/");
			HttpResponse response = this.request(requestUrl);
			JSONObject result = JsonParser.parseToObject(response);
			
			Move move = new Move();
			move.setId(id);
			move.setName(result.getString("name"));
			move.setAccuracy(this.getValidInt(result, "accuracy"));
			move.setEffectChance(this.getValidInt(result, "effect_chance"));
			move.setPp(result.getInt("pp"));
			move.setPriority(result.getInt("priority"));
			move.setPower(this.getValidInt(result, "power"));
			move.setNames(this.getNames(result.getJSONArray("names")));
			move.setEffectEntries(this.getMultipleNames(result.getJSONArray("effect_entries"), "effect", "short_effect"));
			move.setFlavorTextEntries(this.getNames(result.getJSONArray("flavor_text_entries"), "flavor_text"));
			move.setType(this.getNamedResource(result.getJSONObject("type"), Type.class));
			
			this.bufferedMoves.put(id, move);
			return move;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Species getSpecies(int id) {
		if(this.bufferedSpecies.containsKey(id))
			return this.bufferedSpecies.get(id);
		
		try {
			String requestUrl = this.getUrl("pokemon-species/" + id + "/");
			HttpResponse response = this.request(requestUrl);
			JSONObject result = JsonParser.parseToObject(response);
			
			Species species = new Species();
			species.setId(id);
			species.setName(result.getString("name"));
			species.setOrder(result.getInt("order"));
			species.setGenderRate(result.getInt("gender_rate"));
			species.setCaptureRate(result.getInt("capture_rate"));
			species.setBaseHappiness(result.getInt("base_happiness"));
			species.setBaby(result.getBoolean("is_baby"));
			species.setHatchCounter(result.getInt("hatch_counter"));
			species.setColor(result.getJSONObject("color").getString("name"));
			species.setShape(this.hasValidKey(result, "shape") ? result.getJSONObject("shape").getString("name") : null);	
			species.setNames(this.getNames(result.getJSONArray("names")));
			species.setFlavorTextEntries(this.getNames(result.getJSONArray("flavor_text_entries"), "flavor_text"));

			this.bufferedSpecies.put(id, species);
			return species;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Stat getStat(int id) {
		if(this.bufferedStats.containsKey(id))
			return this.bufferedStats.get(id);
		
		try {
			String requestUrl = this.getUrl("stat/" + id + "/");
			HttpResponse response = this.request(requestUrl);
			JSONObject result = JsonParser.parseToObject(response);
			
			Stat stat = new Stat();
			stat.setId(id);
			stat.setName(result.getString("name"));
			stat.setGameIndex(result.getInt("game_index"));
			stat.setBattleOnly(result.getBoolean("is_battle_only"));
			stat.setNames(this.getNames(result.getJSONArray("names")));
			
			this.bufferedStats.put(id, stat);
			return stat;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Type getType(int id) {
		if(this.bufferedTypes.containsKey(id))
			return this.bufferedTypes.get(id);
		
		try {
			String requestUrl = this.getUrl("type/" + id + "/");
			HttpResponse response = this.request(requestUrl);
			JSONObject result = JsonParser.parseToObject(response);
			
			Type type = new Type();
			type.setId(id);
			type.setName(result.getString("name"));
			type.setNames(this.getNames(result.getJSONArray("names")));
			type.setMoves(this.getNamedResourceList(result.getJSONArray("moves"), Move.class));
			type.setPokemons(this.getNamedResourceList("pokemon", result.getJSONArray("pokemon"), Pokemon.class));
			
			this.bufferedTypes.put(id, type);
			return type;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
