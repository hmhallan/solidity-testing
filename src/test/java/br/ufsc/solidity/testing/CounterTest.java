package br.ufsc.solidity.testing;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import br.ufsc.solidity.contracts.Counter;


public class CounterTest {

	private static final String ETHEREUM_URL = "http://localhost:7545";
	private static final String WALLET_PRIVATE_KEY = "9b7227d97bc62fc8d7cf4413a739e85abef0d4e06796a76b8ac576f67d522a2d";
	
	private Counter counter;
	
	@Before
	public void setUp() throws Exception {
		this.counter = this.deployContract();
	}
	
	@Test
	public void constructor_value() throws Exception {
		BigInteger value = this.counter.getCounter().send();
		assertThat(value).isNotNull().isEqualByComparingTo(new BigInteger("5"));
	}
	
	@Test
	public void set_value() throws Exception {
		this.counter.setCounter( new BigInteger("10") ).send();
		
		BigInteger value = this.counter.getCounter().send();
		assertThat(value).isNotNull().isEqualByComparingTo(new BigInteger("10"));
	}
	
	private Counter deployContract() throws Exception {
		Web3j web3j = Web3j.build(new HttpService(ETHEREUM_URL));
		Credentials credentials = Credentials.create(WALLET_PRIVATE_KEY);
		return Counter.deploy(web3j, credentials, new DefaultGasProvider()).send();
	}
}
