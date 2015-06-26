package it.dipe.opencup.model;

import it.dipe.opencup.model.common.AbstractCommonEntity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "D_SNA_SCON_CONTATTACI")
public class Contattaci extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7814153892889614789L;

	@Id
	@GenericGenerator(name = "generator", strategy = "sequence-identity", parameters = @Parameter(name = "sequence", value = "SEQU_SCON_ID"))
	@GeneratedValue(generator = "generator")
	@Column(name = "SEQU_SCON_ID")
	private Integer id;

	@Column(name = "CODI_SCON_CUP", length = 15)
	private String cup;

	@Column(name = "DESC_SCON_NOME", length = 200)
	private String nome;

	@Column(name = "DESC_SCON_COGNOME", length = 200)
	private String cognome;

	@Column(name = "TEXT_SCON_EMAIL", length = 200)
	private String email;

	@Column(name = "TEXT_SCON_OGGETTO", length = 4000)
	private String oggetto;

	@Column(name = "TEXT_SCON_MESSAGGIO", length = 4000)
	private String messaggio;

	@Column(name = "DESC_SCON_TIPO_MESSAGGIO", length = 4000)
	private String tipoMessaggio;

	@Column(name = "DATA_SCON_INSERIMENTO")
	private Date dataInserimento;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCup() {
		return cup;
	}

	public void setCup(String cup) {
		this.cup = cup;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}

	public String getTipoMessaggio() {
		return tipoMessaggio;
	}

	public void setTipoMessaggio(String tipoMessaggio) {
		this.tipoMessaggio = tipoMessaggio;
	}

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

}
