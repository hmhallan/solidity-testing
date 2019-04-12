package br.ufsc.solidity.testing;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple9;
import org.web3j.tx.gas.DefaultGasProvider;

import br.ufsc.solidity.contracts.Democracy;

public class DemocracyTest {

	private static final String ETHEREUM_URL = "http://localhost:7545";
	private static final String WALLET_PRIVATE_KEY = "9b7227d97bc62fc8d7cf4413a739e85abef0d4e06796a76b8ac576f67d522a2d";
	
	private Democracy democracy;
	
	@Before
	public void setUp() throws Exception {
		this.democracy = this.deployContract();
	}
	
	@Test
	public void create_new_proposal() throws Exception {
		//create new proposal (at index 0)
		this.democracy.createProposal("Title", "Description", new BigInteger("1"), new BigInteger("1")).send();
		
		//contracts don't return objects, only tuples
		Tuple9<BigInteger, String, String, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> tp = 
				this.democracy.getProposal(new BigInteger("0")).send();
		
		assertThat(tp).isNotNull();
		assertThat(tp.getValue2()).isEqualTo("Title");
		assertThat(tp.getValue3()).isEqualTo("Description");
	}
	
	private Democracy deployContract() throws Exception {
		Web3j web3j = Web3j.build(new HttpService(ETHEREUM_URL));
		Credentials credentials = Credentials.create(WALLET_PRIVATE_KEY);
		return Democracy.deploy(web3j, credentials, new DefaultGasProvider()).send();
	}
}
