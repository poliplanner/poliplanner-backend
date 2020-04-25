package com.poliplanner.excel;

import com.poliplanner.domain.model.Aula;
import com.poliplanner.domain.model.Materia;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExcelHelper {

    public static String quitarAcentos(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    // funcion retorna solo los que caben dentro del Size
    public static List<String> splitStringBySize(String s, Integer size){
        List<String> splits = new ArrayList<>(s.length()/size);
        for(int i = 0 ; i+size <= s.length() ; i += size){
            splits.add(s.substring(i, i+size));
        }
        return splits;
    }


    public static Map<String, Aula> mapCodigoAulaToAula(List<Aula> aulas){
        return aulas.stream().collect(Collectors.toMap(Aula::getCodigo, aula -> aula));
    }


    public static Map<String, Materia> mapAsignaturaToMateria(List<String> asignaturas, List<Materia> materias){
        // se asume que asignaturas y materias son listas ordenadas por nombre
        HashMap<String, Materia> asignaturaMateriaMap = new HashMap<>();

        int posMateria = 0;
        boolean buscarMatch = true;
        for(String asignatura : asignaturas){
            buscarMatch = true;
            while(posMateria < materias.size() && buscarMatch){
                asignatura = ExcelHelper.quitarAcentos(asignatura);

                Materia materia = materias.get(posMateria);
                String materiaNombre = ExcelHelper.quitarAcentos(materia.getNombre());

                int comparacion = asignatura.compareTo(materiaNombre);
                if(comparacion == 0 ||
                        asignatura.startsWith(materiaNombre+" ") &&
                                (materiaNombre.startsWith("Electiva") ||
                                        materiaNombre.startsWith("Optativa") ||
                                        asignatura.endsWith("(*)"))){
                    asignaturaMateriaMap.put(asignatura, materia);
                    buscarMatch = false;
                } else if(comparacion < 0){
                    posMateria -= 1;

                    if(posMateria < 0)
                        posMateria = 0;

                    buscarMatch = false;
                }else{
                    posMateria += 1;
                }
            }
        }

        return asignaturaMateriaMap;
    }
}
