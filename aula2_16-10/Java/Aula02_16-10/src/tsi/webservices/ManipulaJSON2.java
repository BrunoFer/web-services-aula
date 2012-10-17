package tsi.webservices;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ManipulaJSON2 {

	public static void main(String[] args) throws JSONException {

		String str="";
		try {
			BufferedReader buffer = new BufferedReader(new FileReader("turma_novo.json"));
			while (buffer.ready()){
				str+=buffer.readLine();
			}
		} catch (IOException e){
			System.out.println("Erro ao manipular arquivo");
		}

		JSONObject turma = new JSONObject(str);

		//Atributos da turma
		System.out.println("\n\t\tNome do curso: "+turma.getString("curso"));
		System.out.println("\t\tNome da turma: "+turma.getString("nometurma"));
		System.out.println("\t\tInstituição: "+turma.getString("instituicao"));
		System.out.print("\n\tDisciplinas:");

		//obtém o objeto "devmovies"
		JSONArray arrayDisciplinas = turma.getJSONArray("disciplinas");

		for (int i=0; i< arrayDisciplinas.length();i++){
			JSONObject t = arrayDisciplinas.getJSONObject(i);
			System.out.println("\n");
			System.out.println("Nome disciplina: "+t.getString("nome"));
			System.out.println("Professor: "+t.getString("professor"));
			System.out.println("Período: "+t.getString("periodo"));
			System.out.println("\n\tBancadas:\n");
			JSONArray arrayBancada = t.getJSONArray("bancadas");

			for (int k=0; k< arrayBancada.length();k++){

				JSONObject a = arrayBancada.getJSONObject(k);
				System.out.print("\n");
				System.out.println("Bancada: "+a.getString("nomebancada"));
				System.out.println("Alunos:");
				JSONArray arrayAlunos = a.getJSONArray("alunos");

				for (int j=0;j< arrayAlunos.length();j++){
					JSONObject b = arrayAlunos.getJSONObject(j);
					System.out.print("\n");
					System.out.println("\tNome do Aluno: "+b.getString("nome"));
					System.out.println("\tNota do Aluno: "+b.getInt("nota"));
					System.out.print("\tId do Aluno: "+b.getInt("id")+"\n");
				}
			}
		}

		System.exit(0);
	}
}
