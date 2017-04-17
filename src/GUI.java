import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

public class GUI 
{
	private JFrame frame;
	private static double width;
	private static double height;
	private static double X_tenth;
	private static double Y_tenth;
	private static String adding_to;
	private static String[] last_accesed = {"nothing", "nothing"};
	private int int_listSelection = 0;
	private static Color unmovable	 = UIManager.getColor("activeCaptionBorder");
	private static boolean Sorted_by_name = false;
	private static boolean Sorted_by_price = false;

	private static JPanel Menu;
	private static JLabel lab_Menu;
	private static JButton but_Open;
	static Color col_bg_Menu = new Color(228,202,149);
	
	private static JPanel Admin;
	private static JDialog dlg_enqAdd;
	private static JDialog dlg_enqMod;
	private static JDialog dlg_listSelection;
	private static JLabel lab_Admin;
	private static JTable table_Products;
	private static JScrollPane sPane_Products;
	private static JButton but_Add;
	private static JButton but_Remove;
	private static JButton but_Modify;
	private static JButton but_DoneAdd;
	private static JButton but_DoneMod;
	private static JButton but_SortN;
	private static JButton but_SortP;
	private static JButton but_Clients;
	private static JButton but_Close;
	private static JTextField txt_Name;
	private static JTextField txt_DepId;
	private static JTextField txt_ItemId;
	private static JTextField txt_Price;
	private static JLabel lab_Name;
	private static JLabel lab_DepId;
	private static JLabel lab_ItemId;
	private static JLabel lab_Price;
	private static JDialog dlg_Error;
	private static JButton but_Retry;
	private static JButton but_Cancel;
	private static JLabel lab_Error;
	private static JComboBox box_Clients;
	private static JButton but_Wlist;
	private static JButton but_ShopCart;
	private static JButton but_listCancel;
	private static JLabel lab_listSelection;
	static Color col_bg_Admin = new Color(255,234,148);
	static private Object[][] rowData;
	static private Object[] columnNames = {"Product", "Product ID", "Dep. ID", "Price"};
	static private int index;
	static DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	private static boolean clients_box_hidden = true;
	public static Customer currentCustomer;
	
	private static JPanel WishList;
	private static JLabel lab_WishList;
	private static JLabel lab_popAdd;
	private static JButton but_Return;
	private static JButton but_WAdd;
	private static JButton but_WRemove;
	private static JTable table_Wishlist;
	private static JTable table_WProducts;
	private static JScrollPane sPane_WishList;
	private static JScrollPane sPane_WProducts;
	static Color col_bg_WishList = new Color(203,77,52);
	static Color col_bg_lab_popAdd = new Color(150,150,150);
	static Color col_fg_lab_popAdd = new Color(0,0,0);
	
	private static JPanel ShoppingCart;
	private static JDialog dlg_Expensive;
	private static JLabel lab_ShoppingCart;
	private static JTable table_ShopCart;
	private static JTable table_SProducts;
	private static JLabel lab_ExError;
	private static JScrollPane sPane_ShopCart;
	private static JScrollPane sPane_SProducts;
	private static JTextArea lab_CartDetails;
	private static JButton but_SAdd;
	private static JButton but_SRemove;
	private static JButton but_Order;
	private static JButton but_Resemnare;
	static Color col_bg_ShoppingCart = new Color(203,77,52);
	private String Balance;
	static Font font1 = new Font("SansSerif", Font.BOLD, 16);
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{		
			public void run()
			{
				try 
				{
					GUI window = new GUI();
					window.frame.setVisible(true);
				} 
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	public GUI() 
	{
		initialize();
	}
	private void initialize()
	{		
		initFrame();
		initMenuComponents();
		defineMenuEvents();
	}
	
	
//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//// General-Use Static Methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	static BufferedImage resizeImage(BufferedImage originalImage, int width, int height) throws IOException
	{  
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
        Graphics2D g = resizedImage.createGraphics();  
        g.drawImage(originalImage, 0, 0, width, height, null);  
        g.dispose();  
        return resizedImage;  
    }
	static JTable initTable(String caz)
	{
		JTable table;
		switch(caz)
		{
		case "Products":
			rowData = Test.load_Products();
			break;
		case "Wishlist":
			rowData = Test.load_WishList();
			break;
		case "Shoppingcart":
			rowData = Test.load_ShoppingCart();
			break;
		default:
			System.out.println("Table_product failed to initialize");
			System.exit(0);
		}
		table = new JTable(rowData, columnNames);
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.setDefaultRenderer(Object.class, centerRenderer);
		table.getColumnModel().getColumn(0).setMaxWidth((int)(2.4*X_tenth));
		table.getColumnModel().getColumn(0).setPreferredWidth((int)(2.4*X_tenth));
		return table;
	}
	
	NumberFormat formatter = new DecimalFormat("#0.00"); 
		
//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//// Creating and initializing components
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	void initFrame()
	{
		frame = new JFrame();
		frame.setBounds(0, 0, 500, 500);
		frame.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int)screenSize.getWidth();
		height = (int)screenSize.getHeight();
		X_tenth = width/10;
		Y_tenth = height/10;
		frame.setSize((int)width, (int)height);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
	}
	
	void initCommonComponents()
	{
		but_Return = new JButton("<html><center>Return to the<br>Admin Area</center></html>");
		but_Return.setBounds((int)(8.7*X_tenth), (int)(0*Y_tenth), (int)(1.3*X_tenth), (int)(1*Y_tenth));
		
		lab_popAdd = new JLabel("Choose the product you wish to add", SwingConstants.CENTER);
		lab_popAdd.setBounds((int)(5*X_tenth), (int)(0*Y_tenth), (int)(3.6*X_tenth), (int)(1*Y_tenth));
		lab_popAdd.setOpaque(true);
		lab_popAdd.setBackground(col_bg_lab_popAdd);
		lab_popAdd.setForeground(col_fg_lab_popAdd);
		lab_popAdd.setBorder(BorderFactory.createLoweredBevelBorder());
		lab_popAdd.setVisible(false);
	}
	
	private void initMenuComponents()
	{	
		Menu = new JPanel();
		Menu.setLayout(null);
		Menu.setBackground(col_bg_Menu);
		frame.getContentPane().add(Menu);
		
		lab_Menu = new JLabel("");
		lab_Menu.setBounds(0, -20, (int)(4*X_tenth), (int)(10*Y_tenth));
		try
		{
			BufferedImage img_Menu = ImageIO.read(new File("Resources\\Menu.jpg"));
			BufferedImage resized_Menu = resizeImage(img_Menu,(int)(4*X_tenth), (int)(10*Y_tenth));
			lab_Menu.setIcon(new ImageIcon(resized_Menu));
		}
		catch (IOException e)
		{
			System.out.println("Failed to load Menu Image");
		}
		Menu.add(lab_Menu);
		
		but_Open = new JButton("Open Store");
		but_Open.setBounds((int)(6.5*X_tenth),(int)( Y_tenth ), (int)X_tenth, (int)Y_tenth);	
		Menu.add(but_Open);
	}
	
	private void initAdminComponents()
	{
		Admin = new JPanel();
		Admin.setFocusable(true);
		Admin.setLayout(null);
		Admin.setBackground(col_bg_Admin);
		frame.getContentPane().add(Admin);
		
		lab_Admin = new JLabel("");
		lab_Admin.setBounds(0, -20, (int)(4*X_tenth), (int)(10*Y_tenth));
		try
		{
			BufferedImage img_Admin = ImageIO.read(new File("Resources\\Admin.jpg"));
			BufferedImage resized_Admin = resizeImage(img_Admin,(int)(4*X_tenth), (int)(10*Y_tenth));
			lab_Admin.setIcon(new ImageIcon(resized_Admin));
		}
		catch (IOException e)
		{
			System.out.println("Failed to load Admin Image");
		}
		Admin.add(lab_Admin);
		
//MAIN PAGE		
		table_Products = initTable("Products");
		sPane_Products = new JScrollPane(table_Products);
		sPane_Products.setBounds((int)(5*X_tenth), (int)(1*Y_tenth), (int)(3.6*X_tenth), (int)(8*Y_tenth));
		Admin.add(sPane_Products);
		
		but_Add = new JButton("");
		but_Add.setBounds((int)(8.8*X_tenth), (int)(4*Y_tenth), (int)(0.3*X_tenth), (int)(0.5*Y_tenth));
		try
		{
			BufferedImage img_plus = ImageIO.read(new File("Resources\\plus.png"));
			BufferedImage resized_plus = resizeImage(img_plus,(int)(0.45*X_tenth), (int)(0.75*Y_tenth));
			but_Add.setIcon(new ImageIcon(resized_plus));
		}
		catch (IOException e)
		{
			System.out.println("Failed to load Plus Image");
		}
		Admin.add(but_Add);
		
		but_Modify = new JButton("");
		but_Modify.setBounds((int)(8.8*X_tenth), (int)(4.6*Y_tenth), (int)(0.3*X_tenth), (int)(0.5*Y_tenth));
		try
		{
			BufferedImage img_modify = ImageIO.read(new File("Resources\\modify.png"));
			BufferedImage resized_modify = resizeImage(img_modify,(int)(0.5*X_tenth), (int)(0.82*Y_tenth));
			but_Modify.setIcon(new ImageIcon(resized_modify));
		}
		catch (IOException e)
		{
			System.out.println("Failed to load Modify Image");
		}
		Admin.add(but_Modify);
		
		but_Remove = new JButton("");
		but_Remove.setBounds((int)(8.8*X_tenth), (int)(5.2*Y_tenth), (int)(0.3*X_tenth), (int)(0.5*Y_tenth));	
		try
		{
			BufferedImage img_remove = ImageIO.read(new File("Resources\\remove.png"));
			BufferedImage resized_remove = resizeImage(img_remove,(int)(0.45*X_tenth), (int)(0.75*Y_tenth));
			but_Remove.setIcon(new ImageIcon(resized_remove));
		}
		catch (IOException e)
		{
			System.out.println("Failed to load Remove Image");
		}
		Admin.add(but_Remove);
		
		but_SortN = new JButton("Sort by Name");
		but_SortN.setBounds((int)(5*X_tenth), (int)(0.6*Y_tenth), (int)(1.81*X_tenth), (int)(0.4*Y_tenth));
		Admin.add(but_SortN);
		
		but_SortP = new JButton("Sort by Price");
		but_SortP.setBounds((int)(6.81*X_tenth), (int)(0.6*Y_tenth), (int)(1.8*X_tenth), (int)(0.4*Y_tenth));
		Admin.add(but_SortP);
		
		but_Clients = new JButton("Clients");
		but_Clients.setBounds((int)(9*X_tenth), (int)(0*Y_tenth), (int)(1*X_tenth), (int)(0.5*Y_tenth));
		try
		{
			BufferedImage img_customers = ImageIO.read(new File("Resources\\customers.jpg"));
			BufferedImage resized_customers = resizeImage(img_customers,(int)(1*X_tenth), (int)(0.5*Y_tenth));
			but_Clients.setIcon(new ImageIcon(resized_customers));
		}
		catch (IOException e)
		{
			System.out.println("Failed to load Customers Image");
		}
		Admin.add(but_Clients);
		
		Object[] clientsStrings = Test.load_Clients();
		box_Clients = new JComboBox(clientsStrings);
		box_Clients.setBounds((int)(9*X_tenth), (int)(0.5*Y_tenth), (int)(1*X_tenth), (int)(0.3*Y_tenth));
		box_Clients.setVisible(false);
		Admin.add(box_Clients);
		
		but_Close = new JButton("Close Store");
		but_Close.setBounds((int)(4*X_tenth), (int)(0*Y_tenth), (int)(1*X_tenth), (int)(0.5*Y_tenth));
		Admin.add(but_Close);
//ADD ENQUIRE		
		dlg_enqAdd = new JDialog(frame, "", JDialog.ModalityType.DOCUMENT_MODAL);
		dlg_enqAdd.setLayout(null);
		dlg_enqAdd.setVisible(false);
		dlg_enqAdd.setBounds((int)(5.03*X_tenth), (int)(1.5*Y_tenth), (int)(3.59*X_tenth), (int)(3*Y_tenth));
		dlg_enqAdd.setUndecorated(true);
		dlg_enqAdd.getRootPane().setBorder(BorderFactory.createLineBorder(unmovable, 4));
		
		but_DoneAdd = new JButton("Done");
		but_DoneAdd.setBounds((int)(1.4*X_tenth), (int)(2.35*Y_tenth), (int)(0.8*X_tenth), (int)(0.5*Y_tenth));
		
		txt_Name = new JTextField("");
		txt_Name.setBounds((int)(1.8*X_tenth), (int)(0.3*Y_tenth), (int)(1.2*X_tenth), (int)(0.3*Y_tenth));
		dlg_enqAdd.add(txt_Name);
		
		txt_ItemId = new JTextField("");
		txt_ItemId.setBounds((int)(1.8*X_tenth), (int)(0.8*Y_tenth), (int)(1.2*X_tenth), (int)(0.3*Y_tenth));
		dlg_enqAdd.add(txt_ItemId);
		
		txt_DepId = new JTextField("");
		txt_DepId.setBounds((int)(1.8*X_tenth), (int)(1.3*Y_tenth), (int)(1.2*X_tenth), (int)(0.3*Y_tenth));
		dlg_enqAdd.add(txt_DepId);
				
		txt_Price = new JTextField("");
		txt_Price.setBounds((int)(1.8*X_tenth), (int)(1.8*Y_tenth), (int)(1.2*X_tenth), (int)(0.3*Y_tenth));
		dlg_enqAdd.add(txt_Price);
		
		lab_Name = new JLabel("Product Name:");
		lab_Name.setBounds((int)(0.5*X_tenth), (int)(0.3*Y_tenth), (int)(1.2*X_tenth), (int)(0.3*Y_tenth));
		dlg_enqAdd.add(lab_Name);
		
		lab_ItemId = new JLabel("Product ID:");
		lab_ItemId.setBounds((int)(0.5*X_tenth), (int)(0.8*Y_tenth), (int)(1.2*X_tenth), (int)(0.3*Y_tenth));
		dlg_enqAdd.add(lab_ItemId);
		
		lab_DepId = new JLabel("Department ID:");
		lab_DepId.setBounds((int)(0.5*X_tenth), (int)(1.3*Y_tenth), (int)(1.2*X_tenth), (int)(0.3*Y_tenth));
		dlg_enqAdd.add(lab_DepId);
		
		lab_Price = new JLabel("Product Price:");
		lab_Price.setBounds((int)(0.5*X_tenth), (int)(1.8*Y_tenth), (int)(1.2*X_tenth), (int)(0.3*Y_tenth));
		dlg_enqAdd.add(lab_Price);
		
//MODIFY ENQUIRE		
		dlg_enqMod = new JDialog(frame, "", JDialog.ModalityType.DOCUMENT_MODAL);
		dlg_enqMod.setLayout(null);
		dlg_enqMod.setVisible(false);
		dlg_enqMod.setBounds((int)(5.03*X_tenth), (int)(1.5*Y_tenth), (int)(3.59*X_tenth), (int)(3*Y_tenth));
		dlg_enqMod.setUndecorated(true);
		dlg_enqMod.getRootPane().setBorder(BorderFactory.createLineBorder(unmovable, 4));
		
		but_DoneMod = new JButton("Done");
		but_DoneMod.setBounds((int)(1.4*X_tenth), (int)(2.35*Y_tenth), (int)(0.8*X_tenth), (int)(0.5*Y_tenth));
		dlg_enqMod.add(but_DoneMod);
		
//ERROR POP-UP	
		dlg_Error = new JDialog(frame, "", JDialog.ModalityType.DOCUMENT_MODAL);
		dlg_Error.setLayout(null);
		dlg_Error.setVisible(false);
		dlg_Error.setBounds((int)(5.03*X_tenth), (int)(1.5*Y_tenth), (int)(3.59*X_tenth), (int)(3*Y_tenth));
		dlg_Error.setUndecorated(true);		
		dlg_Error.getRootPane().setBorder(BorderFactory.createLineBorder(unmovable, 20));
		
		but_Retry = new JButton("Retry");
		but_Retry.setBounds((int)(0.5*X_tenth), (int)(2*Y_tenth), (int)(0.8*X_tenth), (int)(0.4*Y_tenth));
		dlg_Error.add(but_Retry);
		
		but_Cancel = new JButton("Cancel");
		but_Cancel.setBounds((int)(2*X_tenth), (int)(2*Y_tenth), (int)(0.8*X_tenth), (int)(0.4*Y_tenth));
		dlg_Error.add(but_Cancel);
		
		lab_Error = new JLabel("<html><center><b>Invalid Operation</b><br>Please make sure you have selected a product (if you wish to modify or remove) and all the input is valid (every product has an unique name and ID).</center></html>");
		lab_Error.setBounds((int)(0.8*X_tenth), (int)(0.1*Y_tenth), (int)(2*X_tenth), (int)(1.5*Y_tenth));
		dlg_Error.add(lab_Error);
//LIST SELECTION
		dlg_listSelection = new JDialog(frame, "", JDialog.ModalityType.DOCUMENT_MODAL);
		dlg_listSelection.setLayout(null);
		dlg_listSelection.setVisible(false);
		dlg_listSelection.setBounds((int)(5.03*X_tenth), (int)(1.5*Y_tenth), (int)(3.59*X_tenth), (int)(3*Y_tenth));
		dlg_listSelection.setUndecorated(true);		
		dlg_listSelection.getRootPane().setBorder(BorderFactory.createLineBorder(unmovable, 20));
		
		but_Wlist = new JButton("WishList");
		but_Wlist.setBounds((int)(0.15*X_tenth), (int)(2*Y_tenth), (int)(0.8*X_tenth), (int)(0.4*Y_tenth));
		dlg_listSelection.add(but_Wlist);
		
		but_ShopCart = new JButton("Shopping Cart");
		but_ShopCart.setBounds((int)(1.15*X_tenth), (int)(2*Y_tenth), (int)(1*X_tenth), (int)(0.4*Y_tenth));
		dlg_listSelection.add(but_ShopCart);
		
		but_listCancel = new JButton("Cancel");
		but_listCancel.setBounds((int)(2.35*X_tenth), (int)(2*Y_tenth), (int)(0.8*X_tenth), (int)(0.4*Y_tenth));
		dlg_listSelection.add(but_listCancel);
		
		initCommonComponents();
		defineCommonEvents();
		
	}
	
	void initBalance()
	{		 
		Balance = "Total Price: " + formatter.format(currentCustomer.cart.getTotalPrice()) + "\nBudget: " + formatter.format(currentCustomer.cart.budget);
		lab_CartDetails = new JTextArea(Balance);
		lab_CartDetails.setFont(font1);
		lab_CartDetails.setBackground(col_bg_ShoppingCart);
		lab_CartDetails.setBounds((int)(8.7*X_tenth), (int)(1*Y_tenth), (int)(1.3*X_tenth), (int)(1*Y_tenth));
		ShoppingCart.add(lab_CartDetails);
		ShoppingCart.repaint();
	}
	
	private void initShoppingCartComponents()
	{
		ShoppingCart = new JPanel();
		ShoppingCart.setFocusable(true);
		ShoppingCart.setLayout(null);
		ShoppingCart.setBackground(col_bg_ShoppingCart);
		frame.getContentPane().add(ShoppingCart);
		
		dlg_Expensive = new JDialog(frame, "", JDialog.ModalityType.DOCUMENT_MODAL);
		dlg_Expensive.setLayout(null);
		dlg_Expensive.setVisible(false);
		dlg_Expensive.setBounds((int)(5.03*X_tenth), (int)(1.3*Y_tenth), (int)(3.59*X_tenth), (int)(3*Y_tenth));
		dlg_Expensive.setUndecorated(true);
		dlg_Expensive.getRootPane().setBorder(BorderFactory.createLineBorder(unmovable, 4));
		
		lab_ExError = new JLabel("<html><center><b>Invalid Operation</b><br>Please make sure you the the product you have selected is not already in your cart and you can afford it.</center></html>");
		lab_ExError.setBounds((int)(0.8*X_tenth), (int)(0.1*Y_tenth), (int)(2*X_tenth), (int)(1.5*Y_tenth));
		dlg_Expensive.add(lab_ExError);
		
		but_Resemnare = new JButton("Got it!");
		but_Resemnare.setBounds((int)(1.3*X_tenth), (int)(2*Y_tenth), (int)(1*X_tenth), (int)(0.6*Y_tenth));
		dlg_Expensive.add(but_Resemnare);
				
		initBalance();
		
		System.out.println(currentCustomer);
		lab_ShoppingCart = new JLabel("");
		BufferedImage img_ShoppingCart = null, resized_ShoppingCart = null;
		try
		{
		    img_ShoppingCart = ImageIO.read(new File("Resources\\ShoppingCart.png"));
		    resized_ShoppingCart = resizeImage(img_ShoppingCart,(int)(4*X_tenth), (int)(10*Y_tenth));
		}
		catch (IOException e)
		{
			System.out.println("Failed to load ShoppingCart Image");
		}
		lab_ShoppingCart.setIcon(new ImageIcon(resized_ShoppingCart));
		lab_ShoppingCart.setBounds(0, -20, (int)(4*X_tenth), (int)(10*Y_tenth));
		ShoppingCart.add(lab_ShoppingCart);
		
		table_ShopCart = initTable("Shoppingcart");
		sPane_ShopCart = new JScrollPane(table_ShopCart);
		sPane_ShopCart.setBounds((int)(5*X_tenth), (int)(2.5*Y_tenth), (int)(3.6*X_tenth), (int)(5*Y_tenth));
		ShoppingCart.add(sPane_ShopCart);
		
		but_SAdd = new JButton("");
		but_SAdd.setBounds((int)(8.8*X_tenth), (int)(4*Y_tenth), (int)(0.3*X_tenth), (int)(0.5*Y_tenth));
		try
		{
			BufferedImage img_plus = ImageIO.read(new File("Resources\\plus.png"));
			BufferedImage resized_plus = resizeImage(img_plus,(int)(0.48*X_tenth), (int)(0.85*Y_tenth));
			but_SAdd.setIcon(new ImageIcon(resized_plus));
		}
		catch (IOException e)
		{
			System.out.println("Failed to load Plus Image");
		}
		ShoppingCart.add(but_SAdd);
		
		but_Order = new JButton("");
		but_Order.setBounds((int)(8.8*X_tenth), (int)(4.6*Y_tenth), (int)(0.3*X_tenth), (int)(0.5*Y_tenth));
		try
		{
			BufferedImage img_order = ImageIO.read(new File("Resources\\order.jpg"));
			BufferedImage resized_modify = resizeImage(img_order,(int)(0.9*X_tenth), (int)(1.5*Y_tenth));
			but_Order.setIcon(new ImageIcon(resized_modify));
		}
		catch (IOException e)
		{
			System.out.println("Failed to load Order Image");
		}
		ShoppingCart.add(but_Order);
		
		but_SRemove = new JButton("");
		but_SRemove.setBounds((int)(8.8*X_tenth), (int)(5.2*Y_tenth), (int)(0.3*X_tenth), (int)(0.5*Y_tenth));
		try
		{
			BufferedImage img_remove = ImageIO.read(new File("Resources\\remove.png"));
			BufferedImage resized_remove = resizeImage(img_remove,(int)(0.48*X_tenth), (int)(0.85*Y_tenth));
			but_SRemove.setIcon(new ImageIcon(resized_remove));
		}
		catch (IOException e)
		{
			System.out.println("Failed to load Remove Image");
		}
		ShoppingCart.add(but_SRemove);
		
		table_SProducts = initTable("Products");
		sPane_SProducts = new JScrollPane(table_SProducts);
		sPane_SProducts.setBounds((int)(5*X_tenth), (int)(1*Y_tenth), (int)(3.6*X_tenth), (int)(8*Y_tenth));
		sPane_SProducts.setVisible(false);
		ShoppingCart.add(sPane_SProducts);
				
		ShoppingCart.add(but_Return);
		
		ShoppingCart.add(lab_popAdd);
				
	}
	
	void initWislistComponents()
	{
		WishList = new JPanel();
		WishList.setFocusable(true);
		WishList.setLayout(null);
		WishList.setBackground(col_bg_WishList);
		frame.getContentPane().add(WishList);
		
		lab_WishList = new JLabel("");
		BufferedImage img_WishList = null, resized_WishList = null;
		try
		{
		    img_WishList = ImageIO.read(new File("Resources\\WishList.jpg"));
		    resized_WishList = resizeImage(img_WishList,(int)(4*X_tenth), (int)(10*Y_tenth));
		}
		catch (IOException e)
		{
			System.out.println("Failed to load WishList Image");
		}
		lab_WishList.setIcon(new ImageIcon(resized_WishList));
		lab_WishList.setBounds(0, -20, (int)(4*X_tenth), (int)(10*Y_tenth));
		WishList.add(lab_WishList);
		
		table_Wishlist = initTable("Wishlist");
		sPane_WishList = new JScrollPane(table_Wishlist);
		sPane_WishList.setBounds((int)(5*X_tenth), (int)(2.5*Y_tenth), (int)(3.6*X_tenth), (int)(5*Y_tenth));
		WishList.add(sPane_WishList);
		
		but_WAdd = new JButton("");
		but_WAdd.setBounds((int)(8.8*X_tenth), (int)(4*Y_tenth), (int)(0.3*X_tenth), (int)(0.5*Y_tenth));
		try
		{
			BufferedImage img_plus = ImageIO.read(new File("Resources\\plus.png"));
			BufferedImage resized_plus = resizeImage(img_plus,(int)(0.48*X_tenth), (int)(0.85*Y_tenth));
			but_WAdd.setIcon(new ImageIcon(resized_plus));
		}
		catch (IOException e)
		{
			System.out.println("Failed to load Plus Image");
		}		
		WishList.add(but_WAdd);
		
		but_WRemove = new JButton("");
		but_WRemove.setBounds((int)(8.8*X_tenth), (int)(5.2*Y_tenth), (int)(0.3*X_tenth), (int)(0.5*Y_tenth));
		try
		{
			BufferedImage img_minus = ImageIO.read(new File("Resources\\remove.png"));
			BufferedImage resized_minus = resizeImage(img_minus,(int)(0.48*X_tenth), (int)(0.85*Y_tenth));
			but_WRemove.setIcon(new ImageIcon(resized_minus));
		}
		catch (IOException e)
		{
			System.out.println("Failed to load Remove Image");
		}
		WishList.add(but_WRemove);
		
		table_WProducts = initTable("Products");
		sPane_WProducts = new JScrollPane(table_WProducts);
		sPane_WProducts.setBounds((int)(5*X_tenth), (int)(1*Y_tenth), (int)(3.6*X_tenth), (int)(8*Y_tenth));
		sPane_WProducts.setVisible(false);
		WishList.add(sPane_WProducts);
		
		WishList.add(lab_popAdd);
		
		WishList.add(but_Return);
	}
	
	
//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//// Defining events
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void defineCommonEvents()
	{
		but_Return.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				if(WishList != null)
					WishList.setVisible(false);
				if(ShoppingCart != null)
					ShoppingCart.setVisible(false);
				Menu.setVisible(false);
				Admin.setVisible(true);
			}
		});
	}
	
	private void defineMenuEvents()
	{
		but_Open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				Test.InitialLoad();
				initAdminComponents();
				defineAdminEvents();
				Menu.setVisible(false);
				Admin.setVisible(true);		
			}
		});	
	}
	private void defineAdminEvents()
	{
		but_Close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				System.exit(0);
			}
		});
		
		MouseListener[] MickyMouse = table_Products.getMouseListeners();
		if((MickyMouse !=null) && (MickyMouse[0] != null))
			table_Products.removeMouseListener(MickyMouse[0]);
		but_Add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				dlg_enqAdd.add(txt_Name);
				dlg_enqAdd.add(txt_ItemId);
				dlg_enqAdd.add(txt_DepId);
				dlg_enqAdd.add(txt_Price);
				dlg_enqAdd.add(lab_Name);
				dlg_enqAdd.add(lab_ItemId);
				dlg_enqAdd.add(lab_DepId);
				dlg_enqAdd.add(lab_Price);
				dlg_enqAdd.add(but_DoneAdd);
				dlg_enqAdd.setVisible(true);
			}
		});
		but_DoneAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				outerloop:
				try 
				{ 
					int itID = (int)Integer.parseInt(txt_ItemId.getText());
					int depID = (int)Integer.parseInt(txt_DepId.getText());
					double price = (double)Double.parseDouble(txt_Price.getText());
					for(Department dep : Test.mystore.departments)
					{
						if(dep.ID == depID)
						{
							index = Test.mystore.departments.indexOf(dep);
							for(Item it : dep.items)
							{
								if(it.ID == itID)
								{
									throw new NumberFormatException();	
								}
							}
							dep.items.add(new Item(txt_Name.getText(), itID, price, depID));
							Test.mystore.departments.set(index, dep);
							table_Products = initTable("Products");
							if(Sorted_by_name)
								but_SortN.doClick();
							if(Sorted_by_price)
								but_SortP.doClick();
							Admin.remove(sPane_Products);
							sPane_Products = new JScrollPane(table_Products);
							sPane_Products.setBounds((int)(5*X_tenth), (int)(1*Y_tenth), (int)(3.6*X_tenth), (int)(8*Y_tenth));
							Admin.add(sPane_Products);
							dlg_enqAdd.setVisible(false);
							break outerloop;
						}
					}
					throw new NumberFormatException();										
				}
				catch (NumberFormatException e)
				{
					dlg_Error.setVisible(true);
				}
				txt_Name.setText("");
				txt_ItemId.setText("");
				txt_DepId.setText("");
				txt_Price.setText("");
			}
		});
		
		but_Modify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				if(table_Products.getSelectedRow() == -1)
					dlg_Error.setVisible(true);
				else
				{
					dlg_enqMod.add(txt_Name);
					dlg_enqMod.add(txt_ItemId);
					dlg_enqMod.add(txt_DepId);
					dlg_enqMod.add(txt_Price);
					dlg_enqMod.add(lab_Name);
					dlg_enqMod.add(lab_ItemId);
					dlg_enqMod.add(lab_DepId);
					dlg_enqMod.add(lab_Price);
					dlg_enqMod.setVisible(true);
				}
			}
		});	
		but_DoneMod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{				
				try 
				{ 
					if(table_Products.getSelectedRow() == -1)
						throw new NumberFormatException();
					String nume = (String)table_Products.getModel().getValueAt(table_Products.getSelectedRow(),0);
					int itID = (int)Integer.parseInt(txt_ItemId.getText());
					int depID = (int)Integer.parseInt(txt_DepId.getText());
					double price = (double)Double.parseDouble(txt_Price.getText());
					boolean valid_dep = false;
					for(Department dep : Test.mystore.departments)
					{
						if(dep.ID == depID)
							valid_dep = true;
						for(Item it: dep.items)
						{
							if( (!(it.name.equals(nume))) && ((it.ID == itID) || (it.name.equals(txt_Name.getText()))) )
							{	
								throw new NumberFormatException();
							}
						}
					}
					if(!valid_dep)
						throw new NumberFormatException();
					outerloop:
					for(Department dep : Test.mystore.departments)
					{
						for(Item it: dep.items)
						{
							if(it.name.equals(nume))
							{
								index = Test.mystore.departments.indexOf(dep);
								dep.items.remove(it);				
								Test.mystore.departments.set(index, dep);
								dep.notifyAllObservers(new Notification(NotificationType.REMOVE,dep.ID,it.ID));
								break outerloop;
							}
						}
					}
					index = Test.mystore.departments.indexOf(Test.mystore.getDepartment(depID));
					Test.mystore.getDepartment(depID).items.add(new Item(txt_Name.getText(), itID, price, depID));
					Test.mystore.departments.set(index, Test.mystore.getDepartment(depID));
					table_Products = initTable("Products");
					if(Sorted_by_name)
						but_SortN.doClick();
					if(Sorted_by_price)
						but_SortP.doClick();
					Admin.remove(sPane_Products);
					sPane_Products = new JScrollPane(table_Products);
					sPane_Products.setBounds((int)(5*X_tenth), (int)(1*Y_tenth), (int)(3.6*X_tenth), (int)(8*Y_tenth));
					Admin.add(sPane_Products);
					dlg_enqMod.setVisible(false);
				}
				catch (NumberFormatException e)
				{
					System.out.println("Error");
					dlg_Error.setVisible(true);
				}
				txt_Name.setText("");
				txt_ItemId.setText("");
				txt_DepId.setText("");
				txt_Price.setText("");
			}
		});
		
		but_Remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					System.out.println(table_Products.getSelectedRow());
					if(table_Products.getSelectedRow() == -1)
						throw new NumberFormatException();
					String nume = (String)table_Products.getModel().getValueAt(table_Products.getSelectedRow(),0);
					outerloop:
					for (Department dep: Test.mystore.departments)
					{
						for(Item it: dep.items)
						{
							if(it.name.equals(nume))
							{
								index = Test.mystore.departments.indexOf(dep);
								dep.items.remove(it);				
								Test.mystore.departments.set(index, dep);
								dep.notifyAllObservers(new Notification(NotificationType.REMOVE,dep.ID,it.ID));
								table_Products = initTable("Products");
								if(Sorted_by_name)
									but_SortN.doClick();
								if(Sorted_by_price)
									but_SortP.doClick();
								Admin.remove(sPane_Products);
								sPane_Products = new JScrollPane(table_Products);
								sPane_Products.setBounds((int)(5*X_tenth), (int)(1*Y_tenth), (int)(3.6*X_tenth), (int)(8*Y_tenth));					
								Admin.add(sPane_Products);
								dlg_enqAdd.setVisible(false);
								break outerloop;
							}
						}
					}
				}
				catch (NumberFormatException e)
				{
					System.out.println("Error");
					dlg_Error.setVisible(true);
				}
				txt_Name.setText("");
				txt_ItemId.setText("");
				txt_DepId.setText("");
				txt_Price.setText("");
			}
		});
		
		but_Retry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				dlg_Error.setVisible(false);
			}
		});
		but_Cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				dlg_Error.setVisible(false);
				dlg_enqAdd.setVisible(false);
				dlg_enqMod.setVisible(false);
			}
		});
		but_SortN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				int n = 0;
				Item one = new Item("ceva",0,0,0);
				Item two = new Item("ceva",0,0,0);
				Object puppet;
				String puppet1;
				while(true)
				{
					if(table_Products.getModel().getValueAt(n, 0) == null)
						break;
					n++;
				}
	        	Admin.remove(sPane_Products);
				for(int i=0; i<n-1; i++)
				{
					for(int j=0; j<n-1; j++)
					{
						puppet = table_Products.getModel().getValueAt(j, 0);
						puppet1 = puppet.toString();
						one.name = puppet.toString();
						puppet = table_Products.getModel().getValueAt(j+1, 0);
						puppet1 = puppet.toString();
						two.name = puppet.toString();
				        if( one.name.compareTo(two.name) > 0 ) 
				        {
				        	one.ID = (int)(table_Products.getModel().getValueAt(j, 1));
				        	one.depID = (int)(table_Products.getModel().getValueAt(j, 2));
				        	one.price = (double)(table_Products.getModel().getValueAt(j, 3));
				        	two.ID = (int)(table_Products.getModel().getValueAt(j+1, 1));
				        	two.depID = (int)(table_Products.getModel().getValueAt(j+1, 2));
				        	two.price = (double)(table_Products.getModel().getValueAt(j+1, 3));
				        	
				        	table_Products.getModel().setValueAt(two.name, j, 0);
				        	table_Products.getModel().setValueAt(two.ID, j, 1);
				        	table_Products.getModel().setValueAt(two.depID, j, 2);
				        	table_Products.getModel().setValueAt(two.price, j, 3);
				        	
				        	table_Products.getModel().setValueAt(one.name, j+1, 0);
				        	table_Products.getModel().setValueAt(one.ID, j+1, 1);
				        	table_Products.getModel().setValueAt(one.depID, j+1, 2);
				        	table_Products.getModel().setValueAt(one.price, j+1, 3);
				        }
				    }
				}
				Sorted_by_name = true;
	        	sPane_Products = new JScrollPane(table_Products);
				sPane_Products.setBounds((int)(5*X_tenth), (int)(1*Y_tenth), (int)(3.6*X_tenth), (int)(8*Y_tenth));
				Admin.add(sPane_Products);
			}			
		});
		but_SortP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				int n = 0;
				Item one = new Item("ceva",0,0,0);
				Item two = new Item("ceva",0,0,0);
				Object puppet;
				String puppet1;
				while(true)
				{
					if(table_Products.getModel().getValueAt(n, 0) == null)
						break;
					n++;
				}
	        	Admin.remove(sPane_Products);
				for(int i=0; i<n-1; i++)
				{
					for(int j=0; j<n-1; j++)
					{
						System.out.println(table_Products.getModel().getValueAt(j, 3));
						one.price = (double)(table_Products.getModel().getValueAt(j, 3));
						two.price = (double)(table_Products.getModel().getValueAt(j+1, 3));
				        if( one.price > two.price ) 
				        {
				        	puppet = table_Products.getModel().getValueAt(j, 0);
							puppet1 = puppet.toString();
							one.name = puppet.toString();
							puppet = table_Products.getModel().getValueAt(j+1, 0);
							puppet1 = puppet.toString();
							two.name = puppet.toString();
				        	one.ID = (int)(table_Products.getModel().getValueAt(j, 1));
				        	one.depID = (int)(table_Products.getModel().getValueAt(j, 2));
				        	two.ID = (int)(table_Products.getModel().getValueAt(j+1, 1));
				        	two.depID = (int)(table_Products.getModel().getValueAt(j+1, 2));	
				        	
				        	table_Products.getModel().setValueAt(two.name, j, 0);
				        	table_Products.getModel().setValueAt(two.ID, j, 1);
				        	table_Products.getModel().setValueAt(two.depID, j, 2);
				        	table_Products.getModel().setValueAt(two.price, j, 3);
				        	
				        	table_Products.getModel().setValueAt(one.name, j+1, 0);
				        	table_Products.getModel().setValueAt(one.ID, j+1, 1);
				        	table_Products.getModel().setValueAt(one.depID, j+1, 2);
				        	table_Products.getModel().setValueAt(one.price, j+1, 3);
				        }
				    }
				}
				Sorted_by_price = true;
	        	sPane_Products = new JScrollPane(table_Products);
				sPane_Products.setBounds((int)(5*X_tenth), (int)(1*Y_tenth), (int)(3.6*X_tenth), (int)(8*Y_tenth));
				Admin.add(sPane_Products);
			}			
		});
		but_Clients.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				if(clients_box_hidden)
				{
					clients_box_hidden = false;
					box_Clients.setVisible(true);
				}
				else
				{
					clients_box_hidden = true;
					box_Clients.setVisible(false);
				}
			}
		});
		box_Clients.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0)
			{
				for(Customer c : Test.mystore.customers)
				{
					if(c.name.equals((String)box_Clients.getSelectedItem()))
					{
						currentCustomer = c;
						break;
					}
				}
				box_Clients.setVisible(false);
				if(int_listSelection != 0)
					dlg_listSelection.remove(lab_listSelection);
				int_listSelection ++;
				lab_listSelection = new JLabel("Choose what to edit for " + currentCustomer.name);
				lab_listSelection.setBounds((int)(1*X_tenth), (int)(0.1*Y_tenth), (int)(2*X_tenth), (int)(1.5*Y_tenth));
				dlg_listSelection.add(lab_listSelection);
				dlg_listSelection.setVisible(true);
			}	
		});
		but_ShopCart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0)
			{
				last_accesed[0] = last_accesed[1];
				last_accesed[1] = "shoppingcart";
				initShoppingCartComponents();
				dlg_listSelection.setVisible(false);
				Admin.setVisible(false);
				defineShoppingCartEvents();
				ShoppingCart.setVisible(true);
			}
		});
		but_Wlist.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0)
			{
				adding_to = "wishlist";
				last_accesed[0] = last_accesed[1];
				last_accesed[1] = "wishlist";
				initWislistComponents();
				defineWishListEvents();
				dlg_listSelection.setVisible(false);
				Admin.setVisible(false);
				WishList.setVisible(true);
			}
		});
		but_listCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0)
			{
				dlg_listSelection.setVisible(false);
			}
		});
	}
	
	private void defineWishListEvents()
	{
		but_WAdd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0)
			{
				if(last_accesed[0].equals("shoppingcart"))
				{	
					ShoppingCart.remove(lab_popAdd);
					WishList.add(lab_popAdd);
				}		
				lab_popAdd.setVisible(true);
				sPane_WishList.setVisible(false);
				sPane_WProducts.setVisible(true);
			}
		});
		
		table_WProducts.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt)
		    {
		        int row = table_WProducts.rowAtPoint(evt.getPoint());
		        System.out.println("Selectat: " + row);
		        if (row >= 0 )
		        {
		        	Item Added = null;
		        	String name = (String)table_WProducts.getModel().getValueAt(row, 0);
		        	outerloop:
		        	for(Department dep : Test.mystore.departments)
		        	{
		        		for(Item it : dep.items)
		        		{
		        			if(it.name.equals(name))
		        			{
		        				Added = it;
		        				break outerloop;
		        			}
		        		}
		        	}
		        	index = Test.mystore.customers.indexOf(currentCustomer);
		        	System.out.println("Wishlist: " +  currentCustomer.wlist);
		        	System.out.println("Item to add: " + Added);
			        currentCustomer.wlist.add(Added);
		        	Test.mystore.customers.set(index, currentCustomer);
		    		WishList.remove(sPane_WishList);
		        	table_Wishlist = initTable("Wishlist");
		    		sPane_WishList = new JScrollPane(table_Wishlist);
		    		sPane_WishList.setBounds((int)(5*X_tenth), (int)(2.5*Y_tenth), (int)(3.6*X_tenth), (int)(5*Y_tenth));
		    		WishList.add(sPane_WishList);
		    		sPane_WProducts.setVisible(false);
		    		lab_popAdd.setVisible(false);
		        }       
		    }
		});
		
		but_WRemove.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0)
			{
				Item Removed = null;
				index = Test.mystore.customers.indexOf(currentCustomer);
				String name = (String)table_Wishlist.getModel().getValueAt(table_Wishlist.getSelectedRow(),0);
				outerloop:
				for(Department dep : Test.mystore.departments)
	        	{
	        		for(Item it : dep.items)
	        		{
	        			if(it.name.equals(name))
	        			{
	        				Removed = it;
	        				break outerloop;
	        			}
	        		}
	        	}

				currentCustomer.wlist.remove(Removed);
				Test.mystore.customers.set(index, currentCustomer);
	        	table_Wishlist = initTable("Wishlist");
	    		WishList.remove(sPane_WishList);
	    		sPane_WishList = new JScrollPane(table_Wishlist);
	    		sPane_WishList.setBounds((int)(5*X_tenth), (int)(2.5*Y_tenth), (int)(3.6*X_tenth), (int)(5*Y_tenth));
	    		WishList.add(sPane_WishList);
			}
		});		
	}
	
	private void defineShoppingCartEvents()
	{
		but_SAdd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0)
			{
				if(last_accesed[0].equals("wishlist"))
				{
					WishList.remove(lab_popAdd);
					ShoppingCart.add(lab_popAdd);
				}
				lab_popAdd.setVisible(true);
				sPane_ShopCart.setVisible(false);
				lab_popAdd.setVisible(true);
				sPane_SProducts.setVisible(true);
			}
		});
		table_SProducts.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt)
		    {
		        int row = table_SProducts.rowAtPoint(evt.getPoint());
		        System.out.println("Selectat: " + row);
		        if (row >= 0 )
		        {
		        	Item Added = null;
		        	String name = (String)table_SProducts.getModel().getValueAt(row, 0);
		        	outerloop:
		        	for(Department dep : Test.mystore.departments)
		        	{
		        		for(Item it : dep.items)
		        		{
		        			if(it.name.equals(name))
		        			{
		        				Added = it;
		        				break outerloop;
		        			}
		        		}
		        	}
		        	index = Test.mystore.customers.indexOf(currentCustomer);
			        if(!currentCustomer.cart.add(Added))
			        	dlg_Expensive.setVisible(true);
			        ShoppingCart.remove(lab_CartDetails);
			        initBalance();
		        	Test.mystore.customers.set(index, currentCustomer);
		        	table_ShopCart = initTable("Shoppingcart");
		    		ShoppingCart.remove(sPane_ShopCart);
		    		sPane_ShopCart = new JScrollPane(table_ShopCart);
		    		sPane_ShopCart.setBounds((int)(5*X_tenth), (int)(2.5*Y_tenth), (int)(3.6*X_tenth), (int)(5*Y_tenth));
		    		ShoppingCart.add(sPane_ShopCart);
		    		sPane_SProducts.setVisible(false);
		    		lab_popAdd.setVisible(false);
		        }
		        
		    }
		});
		but_SRemove.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0)
			{
				Item Removed = null;
				index = Test.mystore.customers.indexOf(currentCustomer);
				String name = (String)table_ShopCart.getModel().getValueAt(table_ShopCart.getSelectedRow(),0);
				outerloop:
				for(Department dep : Test.mystore.departments)
	        	{
	        		for(Item it : dep.items)
	        		{
	        			if(it.name.equals(name))
	        			{
	        				Removed = it;
	        				break outerloop;
	        			}
	        		}
	        	}
				currentCustomer.cart.remove(Removed);
				ShoppingCart.remove(lab_CartDetails);
				initBalance();
				Test.mystore.customers.set(index, currentCustomer);
	        	table_ShopCart = initTable("Shoppingcart");
	    		ShoppingCart.remove(sPane_ShopCart);
	    		sPane_ShopCart = new JScrollPane(table_ShopCart);
	    		sPane_ShopCart.setBounds((int)(5*X_tenth), (int)(2.5*Y_tenth), (int)(3.6*X_tenth), (int)(5*Y_tenth));
	    		ShoppingCart.add(sPane_ShopCart);
			}
		});
		but_Resemnare.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0)
			{
				dlg_Expensive.setVisible(false);
			}
		});
		but_Order.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0)
			{
				double new_budget = currentCustomer.cart.budget;
				currentCustomer.cart = new ShoppingCart(new_budget);
				table_ShopCart = initTable("Shoppingcart");
	    		ShoppingCart.remove(sPane_ShopCart);
	    		sPane_ShopCart = new JScrollPane(table_ShopCart);
	    		sPane_ShopCart.setBounds((int)(5*X_tenth), (int)(2.5*Y_tenth), (int)(3.6*X_tenth), (int)(5*Y_tenth));
	    		ShoppingCart.add(sPane_ShopCart);
	    		ShoppingCart.remove(lab_CartDetails);
	    		initBalance();
			}
		});
	}	
}