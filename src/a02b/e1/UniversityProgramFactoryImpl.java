package a02b.e1;

import static a02b.e1.UniversityProgram.Sector.COMPUTER_ENGINEERING;
import static a02b.e1.UniversityProgram.Sector.COMPUTER_SCIENCE;
import static a02b.e1.UniversityProgram.Sector.MATHEMATICS;
import static a02b.e1.UniversityProgram.Sector.PHYSICS;
import static a02b.e1.UniversityProgram.Sector.THESIS;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class UniversityProgramFactoryImpl implements UniversityProgramFactory {

    private abstract class UniversityProgramImpl implements UniversityProgram {

        protected Map<String, Pair<Sector, Integer>> map = new HashMap<>();

        protected int getTotalCredits(Set<String> courseNames){
            return courseNames.stream().map(e -> map.get(e)).mapToInt(e -> e.get2()).sum();
        }

        protected int getCreditsForSubj(Set<String> courseNames, Sector sector){
            return 
            courseNames.stream().map(e -> map.get(e)).filter(e -> e.get1() == sector).mapToInt(e -> e.get2()).sum();
        }

        @Override
        public void addCourse(String name, Sector sector, int credits) {
            map.put(name, new Pair<Sector, Integer>(sector, credits));
        }

    }

    @Override
    public UniversityProgram flexible() {

        return new UniversityProgramImpl() {

            @Override
            public boolean isValid(Set<String> courseNames) {
                return getTotalCredits(courseNames) == 60;
            }

        };

    }

    @Override
    public UniversityProgram realistic() {
     return new UniversityProgramImpl() {

            @Override
            public boolean isValid(Set<String> courseNames) {
                int total = getTotalCredits(courseNames);
                int a = getCreditsForSubj(courseNames, COMPUTER_SCIENCE)+getCreditsForSubj(courseNames, COMPUTER_ENGINEERING);
                int b= getCreditsForSubj(courseNames, MATHEMATICS)+getCreditsForSubj(courseNames, PHYSICS);
                int c= getCreditsForSubj(courseNames, THESIS );
                
                if(total==120 && a >= 60 && b<=18 && c ==24 ){
                    return true;
                }

                else return false;
            }

        };

    }

    @Override
    public UniversityProgram scientific() {
          return new UniversityProgramImpl() {

            @Override
            public boolean isValid(Set<String> courseNames) {
                int total = getTotalCredits(courseNames);
                int a = getCreditsForSubj(courseNames, COMPUTER_SCIENCE);
                int b= getCreditsForSubj(courseNames, MATHEMATICS);
                int c= getCreditsForSubj(courseNames, PHYSICS);
                
                if(total==60 && a >= 12 && b>=12 && c >=12 ){
                    return true;
                }

                else return false;
            }

        };
    }

    @Override
    public UniversityProgram shortComputerScience() {
     return new UniversityProgramImpl() {

            @Override
            public boolean isValid(Set<String> courseNames) {
                int total = getTotalCredits(courseNames);
                int a = getCreditsForSubj(courseNames, COMPUTER_SCIENCE)+ getCreditsForSubj(courseNames, COMPUTER_ENGINEERING);
                
                if(total >=48  && a >= 30 ){
                    return true;
                }

                else return false;
            }

        };
    }

}
