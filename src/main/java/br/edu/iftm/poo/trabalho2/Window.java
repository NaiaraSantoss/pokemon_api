package br.edu.iftm.poo.trabalho2;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import com.google.common.collect.Iterables;

import br.edu.iftm.poo.trabalho2.interfaces.IApi;
import br.edu.iftm.poo.trabalho2.interfaces.IFactory;
import br.edu.iftm.poo.trabalho2.interfaces.IWindow;
import br.edu.iftm.poo.trabalho2.models.NamedResource;
import br.edu.iftm.poo.trabalho2.models.NamedResourceList;
import br.edu.iftm.poo.trabalho2.models.Pokemon;
import br.edu.iftm.poo.trabalho2.models.PokemonStat;
import br.edu.iftm.poo.trabalho2.models.Stat;
import br.edu.iftm.poo.trabalho2.models.Type;

@SuppressWarnings("unused")
public class Window implements IWindow {
	
	private static final String[] PokemonStats = 
		{ "hp", "attack", "defense", "special-attack", "special-defense", "speed" };
	private static final String[] PreferedLanguages =
		{ "pt-br", "en" };
	
	private IFactory factory;
	private IApi api; 
	private JFrame frame;
	 
	private JPanel searchPage;
	private JTextField searchBar;
	private JButton searchButton;
	private JButton[] pokemonButtons;
	private JButton nextPage;
	private JButton previousPage;
	
	private int currentPage = 0;
	private String searchString = "";
	
	private JPanel pokemonPage;
	private JButton backButton;
	private JLabel image;
	private JLabel name;
	private JLabel[] types = new JLabel[2];
	private JLabel description;
	private JLabel[] statLabels = new JLabel[Window.PokemonStats.length];
	private JLabel[] statBackgrounds = new JLabel[Window.PokemonStats.length];
	private JLabel[] statBars = new JLabel[Window.PokemonStats.length];
	private JLabel[] statBarValues = new JLabel[Window.PokemonStats.length];
	
	private int _pokemonStatBarWidth = -1;
	private int _pokemonDescriptionWidth = -1;
	
	private Timer changePageTimer = null;
	private JPanel changePanelTo = null;
	
	private NamedResourceList<Pokemon> allPokemons = null;
	private Pokemon currentPokemon = null;
	
	public Window(IFactory factory) {
		this.factory = factory;
		this.api = factory.getAPI();
		this.allPokemons = api.enumeratePokemons();
		
		this.createWindow();
	}

	// ------------------
	
	private void createWindow() {
		final int width = 360;
		final int height = 480;
		
		this.frame = new JFrame("Pokedex");
		this.frame.setSize(width, height);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setResizable(false);
		
		this.createSearchPage();
		this.createPokemonPage();
		
		this.frame.getContentPane().add(this.searchPage);
	}
	
	private void createSearchPage() {
		final int width = 360;
		final int height = 480 - 42;
		final int margin = 5;
		final int hw = (width - 6 * margin);
		
		int x = 2 * margin;
		int y = 20 + 3 * margin;
		int paddingX = 0;
		int paddingY = 0;
		
		final int rows = (height - 40 - 3 * margin) / (20 + margin);
		this.pokemonButtons = new JButton[rows * 2]; 
		for(int i = 0, j = 0; i < rows; i++, j += 2) {
			this.pokemonButtons[j] = new JButton(this.allPokemons.get(j).getName());
			this.pokemonButtons[j].setBounds(x, y, -margin + hw / 2, 20);
			this.pokemonButtons[j].addActionListener(new SearchPagePokemonButtonAction(this, j));
			
			this.pokemonButtons[j + 1] = new JButton(this.allPokemons.get(j + 1).getName());
			this.pokemonButtons[j + 1].setBounds(x + (hw / 2), y, -margin + hw / 2, 20);
			this.pokemonButtons[j + 1].addActionListener(new SearchPagePokemonButtonAction(this, j + 1));
			y += 20 + margin;
		}
		
		y = 2 * margin;
		this.searchBar = new JTextField();
		this.searchBar.setBounds(x, y, hw - 50 - 2 * margin, 20);
		this.searchButton = new JButton("Go");
		this.searchButton.setBounds(x + hw - 50 - margin, y, 50, 20);
		this.searchButton.addActionListener(new SearchPageSearchButtonAction(this));

		this.previousPage = new JButton("←");
		this.previousPage.setBounds(x, height - 20 - margin, -margin + hw / 2, 20);
		this.previousPage.addActionListener(new SearchPageNavigationButtonAction(this, -1));
		this.nextPage = new JButton("→");
		this.nextPage.setBounds(x + (hw / 2), height - 20 - margin, -margin + hw / 2, 20);
		this.nextPage.addActionListener(new SearchPageNavigationButtonAction(this, 1));
		
		// Montar
		this.searchPage = new JPanel(null);
		this.searchPage.add(this.searchBar);
		this.searchPage.add(this.searchButton);
		this.searchPage.add(this.previousPage);
		this.searchPage.add(this.nextPage);
		for(JButton b : this.pokemonButtons) 
			this.searchPage.add(b);
	}
	
	private void createPokemonPage() {
		final int width = 360;
		final int height = 480 - 42;
		final int margin = 5;
		final int hw = (width - 6 * margin);
		
		int x = 2 * margin;
		int y = 2 * margin;
		int paddingX = 0;
		int paddingY = 0;
		
		// Pagina Pokemon
		this.backButton = new JButton("Voltar");
		this.backButton.setBounds(x, y, hw - margin, 20);
		this.backButton.addActionListener(new PokemonPageBackButtonAction(this));
		y += 20 + margin;
		
		// Image
		this.image = new JLabel();
		this.image.setOpaque(true);
		this.image.setBounds(x, y, 100, 100);
		paddingX = 100 + margin;
		x += 100 + margin;
		
		// Name
		y += 100 - 20 - margin - 20;
		this.name = new JLabel("name");
		this.name.setBounds(x, y, hw - paddingX, 20);
		y += 20 + margin;
		
		// Type 1
		this.types[0] = new JLabel("type1");
		this.types[0].setBounds(x, y, -margin + (hw - paddingX) / 2, 20);
		this.types[0].setHorizontalAlignment(JLabel.CENTER);
		this.types[0].setOpaque(true);
		x += (hw - paddingX) / 2;
		
		// Type 2
		this.types[1] = new JLabel("type2");
		this.types[1].setBounds(x, y, -margin + (hw - paddingX) / 2, 20);
		this.types[1].setHorizontalAlignment(JLabel.CENTER);
		this.types[1].setOpaque(true);
		x = 2 * margin;
		y += 20 + margin;
		paddingX = 0;

		// Description
		this._pokemonDescriptionWidth = hw - margin;
		this.description = new JLabel("desc");
		this.description.setOpaque(true);
		this.description.setBounds(x, y, this._pokemonDescriptionWidth, height - y - (this.statLabels.length * (20 + margin)) - margin);
		this.description.setVerticalAlignment(JLabel.TOP);
		
		y = height;
		this._pokemonStatBarWidth = hw - 100 - 2 * margin;
		// Stats
		for(int i = 0; i < this.statLabels.length; i++) {
			int _x = x + 100 + margin;
			int _y = y - (this.statLabels.length * (20 + margin)) + (i * (20 + margin));
			
			// Label
			this.statLabels[i] = new JLabel(Window.PokemonStats[i]);
			this.statLabels[i].setBounds(x, _y, 100, 20);
			
			// Fundo
			this.statBackgrounds[i] = new JLabel("");
			this.statBackgrounds[i].setBounds(_x, _y, this._pokemonStatBarWidth, 20);
			this.statBackgrounds[i].setOpaque(true);
			
			// Barra
			this.statBars[i] = new JLabel("");
			this.statBars[i].setBounds(_x, _y, this._pokemonStatBarWidth / 2, 20);
			this.statBars[i].setOpaque(true);
			this.statBars[i].setBackground(Color.gray);
			
			// Valores
			this.statBarValues[i] = new JLabel("0");
			this.statBarValues[i].setBounds(_x + margin, _y, this._pokemonStatBarWidth - margin, 20);
			this.statBarValues[i].setForeground(Color.white);
		}
		
		// Montar
		this.pokemonPage = new JPanel(null);
		this.pokemonPage.setBackground(Color.white);
		
		this.pokemonPage.add(this.backButton);
		this.pokemonPage.add(this.image);
		this.pokemonPage.add(this.name);
		for(JLabel l : this.types) 
			this.pokemonPage.add(l);
		this.pokemonPage.add(this.description);
		for(JLabel l : this.statLabels) 
			this.pokemonPage.add(l);
		for(JLabel l : this.statBarValues) 
			this.pokemonPage.add(l);
		for(JLabel l : this.statBars) 
			this.pokemonPage.add(l);
		for(JLabel l : this.statBackgrounds) 
			this.pokemonPage.add(l);
	}
	
	private void updateSearchPage() { this.updateSearchPage(this.searchPokemons()); }
	private void updateSearchPage(NamedResourceList<Pokemon> pokemons) {		
		int startIndex = this.currentPage * this.pokemonButtons.length;
		for(int i = 0, j = startIndex; i < this.pokemonButtons.length; i++, j++) {
			this.pokemonButtons[i].setVisible(j < pokemons.size());
			if(this.pokemonButtons[i].isVisible()) 
				this.pokemonButtons[i].setText(pokemons.get(j).getName());
		}
	}
	
	// ------------------
	
	public void setPokemon(Pokemon pokemon) {
		this.currentPokemon = pokemon;
		if(pokemon == null)
			return;
		
		this.name.setText(Window.getLanguageText(pokemon.getSpecies(this.api).getNames()));
		this.description.setText(this.getDescriptionText(Window.getLanguageText(pokemon.getSpecies(this.api).getFlavorTextEntries())));
		
		this.types[0].setText(Window.getLanguageText(pokemon.getTypes(this.api).get(0).getNames()));
		this.types[1].setVisible(pokemon.getTypes().size() > 1);
		if(this.types[1].isVisible())
			this.types[1].setText(Window.getLanguageText(pokemon.getTypes(this.api).get(1).getNames()));
		
		for(int i = 0; i < this.statBars.length; i++) {
			PokemonStat stat = this.getNamedStat(pokemon.getStats(), Window.PokemonStats[i]);
			Rectangle previousBounds = this.statBars[i].getBounds();
			
			// Label
			this.statLabels[i].setText(Window.getLanguageText(stat.getStat(this.api).getNames()));
			// Barra
			this.statBars[i].setBounds(
					previousBounds.x,
					previousBounds.y,
					Math.round((this._pokemonStatBarWidth / 255f) * stat.getBase()),
					previousBounds.height);
			// Valor
			this.statBarValues[i].setText(Integer.toString(stat.getBase()));
		}
		
		// Imagem
		if(pokemon.getSprites().getFrontDefault() != null) {
			try {
				URL url = new URL(pokemon.getSprites().getFrontDefault());
				BufferedImage image = ImageIO.read(url);
				this.image.setIcon(new ImageIcon(image));
			} 
			catch(Exception e) {
				e.printStackTrace();
				System.err.println("Failed image URL: " + pokemon.getSprites().getFrontDefault());
			}
		}
		else {
			this.image.setIcon(new ImageIcon());
		}
		
		this.setPage(this.pokemonPage);
	}
	public Pokemon getPokemon() { return this.currentPokemon; }
	
	@Override
	public void setVisible(boolean isVisible) {
		this.frame.setVisible(isVisible);
	}

	private void setPage(JPanel page) {
		if(this.changePageTimer == null) {
			this.changePageTimer = new Timer(1, new ActionListener() {
				@Override 
				public void actionPerformed(ActionEvent e) {
					frame.getContentPane().removeAll();
					frame.getContentPane().add(changePanelTo);
					frame.revalidate();
					frame.repaint();
				}
			});
			this.changePageTimer.setRepeats(false);
		}
		
		this.changePanelTo = page;
		this.changePageTimer.start();
	}
	
	private void setSearchString(String searchString) {
		if(searchString == null)
			return;

		NamedResourceList<Pokemon> pokemons = searchPokemons(searchString);
		
		// Caso o texto de procura seja o nome do pokemon, ir diretamente a ele
		if(pokemons.size() == 1) {
			if(pokemons.get(0).getName().equalsIgnoreCase(searchString)) {
				this.setPokemon(pokemons.get(0).getResource(this.api));
				return;
			}
		}

		this.searchString = searchString;
		this.currentPage = 0;
		this.updateSearchPage();
	}
	
	// ------------------
	
	private static String getLanguageText(Map<String, String> names) {
		for(String n : Window.PreferedLanguages)
			if(names.containsKey(n))
				return names.get(n);
		return Iterables.getFirst(names.values(), null);
	}
	private static String getLanguageText(Map<String, String[]> names, int index) {
		for(String n : Window.PreferedLanguages)
			if(names.containsKey(n))
				return names.get(n)[index];
		return Iterables.getFirst(names.values(), null)[index];
	}
	
	private String getDescriptionText(String text) {
		return String.format("<html><div style=\"width:%dpx;\">%s</div></html>", 
				this._pokemonDescriptionWidth - 80, 
				text.replace("", "<br>"));
	}
	
	private PokemonStat getNamedStat(PokemonStat[] stats, String statName) {
		for(PokemonStat s : stats)
			if(s.getStat(this.api).getName().contentEquals(statName))
				return s;
		return null;
	}

	public NamedResourceList<Pokemon> searchPokemons() {
		return this.searchPokemons(this.searchString);
	}
	public NamedResourceList<Pokemon> searchPokemons(String searchString) {
		if(searchString.length() == 0)
			return this.allPokemons;

		NamedResourceList<Pokemon> pokemons = new NamedResourceList<Pokemon>(Pokemon.class);
		for(int i = 0; i < this.allPokemons.size(); i++) {
			NamedResource<Pokemon> pokemon = this.allPokemons.get(i);
			if(pokemon.getName().contains(searchString))
				pokemons.add(pokemon);
		}
		return pokemons;
	}
	
	// ------------------
	
	// Acao dos botoes de navegacao
	private class SearchPageNavigationButtonAction implements ActionListener {
		private Window window;
		private int direction;
		
		private int pageItems = -1;
		
		public SearchPageNavigationButtonAction(Window window, int direction) {
			this.window = window;
			this.direction = direction;
			
			this.pageItems = this.window.pokemonButtons.length;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			NamedResourceList<Pokemon> pokemons = this.window.searchPokemons();
			int maxPage = pokemons.size() / this.pageItems;
			
			this.window.currentPage += this.direction;
			if(this.window.currentPage < 0)
				this.window.currentPage = maxPage;
			else if(this.window.currentPage > maxPage)
				this.window.currentPage = 0;
			
			this.window.updateSearchPage(pokemons);
		}
	}
	
	// Acao do botao de procura
	private class SearchPageSearchButtonAction implements ActionListener {
		private Window window;
		
		public SearchPageSearchButtonAction(Window window) {
			this.window = window;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String searchString = this.window.searchBar.getText();
			
			try {
				// Procurar por ID
				int id = Integer.parseInt(searchString);
				if(id < 1 || id > this.window.allPokemons.size() + 1)
					return;
				
				this.window.setPokemon(this.window.allPokemons.get(id - 1).getResource(this.window.api));
				return;
			}
			catch(Exception exception) {
				// Procurar por texto
				this.window.setSearchString(searchString);
				return;
			}
		}
	}
	
	// Acao dos botoes de pokemon
	private class SearchPagePokemonButtonAction implements ActionListener {
		private Window window;
		private int index;
		
		public SearchPagePokemonButtonAction(Window window, int index) {
			this.window = window;
			this.index = index;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			NamedResourceList<Pokemon> pokemons = this.window.searchPokemons();
			int startIndex = this.window.currentPage * this.window.pokemonButtons.length;
			
			this.window.setPokemon(pokemons.get(startIndex + this.index).getResource(this.window.api));
		}
	}
	
	// Acao do botao de voltar
	private class PokemonPageBackButtonAction implements ActionListener {
		private Window window;
		
		public PokemonPageBackButtonAction(Window window) {
			this.window = window;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			this.window.setPage(this.window.searchPage);
		}
	}
}
