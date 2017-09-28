package org.dgfoundation.amp.nireports.testcases.generic.dimensions;

import static org.dgfoundation.amp.nireports.testcases.HNDNode.element;

import java.util.Arrays;
import java.util.List;

import org.dgfoundation.amp.nireports.testcases.HNDNode;
import org.dgfoundation.amp.nireports.testcases.HardcodedNiDimension;
import org.dgfoundation.amp.nireports.testcases.TestModelConstants;



public class LocationsTestDimension extends HardcodedNiDimension {

    public final static LocationsTestDimension instance = new LocationsTestDimension("locs", TestModelConstants.LOCATIONS_DIMENSION_DEPTH);

    public LocationsTestDimension(String name, int depth) {
        super(name, depth);
    }

    @Override
    protected List<HNDNode> buildHardcodedElements() {
        return Arrays.asList(
        element(8845, "Andorra" ), 
        element(8846, "United Arab Emirates" ), 
        element(8847, "Afghanistan" ), 
        element(8848, "Antigua and Barbuda" ), 
        element(8849, "Anguilla" ), 
        element(8850, "Albania" ), 
        element(8851, "Armenia" ), 
        element(8852, "Netherlands Antilles" ), 
        element(8853, "Angola" ), 
        element(8854, "Antarctica" ), 
        element(8855, "Argentina" ), 
        element(8856, "American Samoa" ), 
        element(8857, "Austria" ), 
        element(8858, "Australia" ), 
        element(8859, "Aruba" ), 
        element(8860, "Azerbaijan" ), 
        element(8861, "Bosnia and Herzegovina" ), 
        element(8862, "Barbados" ), 
        element(8863, "Bangladesh" ), 
        element(8864, "Belgium" ), 
        element(8865, "Burkina Faso" ), 
        element(8866, "Bulgaria" ), 
        element(8867, "Bahrain" ), 
        element(8868, "Burundi" ), 
        element(8869, "Benin" ), 
        element(8870, "Bermuda" ), 
        element(8871, "Brunei Darussalam" ), 
        element(8872, "Bolivia" ), 
        element(8873, "Brazil" ), 
        element(8874, "Bahamas" ), 
        element(8875, "Bhutan" ), 
        element(8876, "Bouvet Island" ), 
        element(8877, "Botswana" ), 
        element(8878, "Belarus" ), 
        element(8879, "Belize" ), 
        element(8880, "Canada" ), 
        element(8881, "Cocos (Keeling) Islands" ), 
        element(8882, "Congo, Democratic Republic Of" ), 
        element(8883, "Central African Republic" ), 
        element(8884, "Congo" ), 
        element(8885, "Switzerland" ), 
        element(8886, "Cote d`Ivoire" ), 
        element(8887, "Cook Islands" ), 
        element(8888, "Chile" ), 
        element(8889, "Cameroon" ), 
        element(8890, "China" ), 
        element(8891, "Colombia" ), 
        element(8892, "Costa Rica" ), 
        element(8893, "Cuba" ), 
        element(8894, "Cape Verde" ), 
        element(8895, "Christmas Island" ), 
        element(8896, "Cyprus" ), 
        element(8897, "Czech Republic" ), 
        element(8898, "Germany" ), 
        element(8899, "Djibouti" ), 
        element(8900, "Denmark" ), 
        element(8901, "Dominica" ), 
        element(8902, "Dominican Republic" ), 
        element(8903, "Algeria" ), 
        element(8904, "Ecuador" ), 
        element(8905, "Estonia" ), 
        element(8906, "Egypt" ), 
        element(8907, "Western Sahara" ), 
        element(8908, "Eritrea" ), 
        element(8909, "Spain" ), 
        element(8910, "Ethiopia" ), 
        element(8911, "Finland" ), 
        element(8912, "Fiji" ), 
        element(8913, "Falkland Islands (Malvinas)" ), 
        element(8914, "Micronesia" ), 
        element(8915, "Faroe Islands" ), 
        element(8916, "France" ), 
        element(8917, "Gabon" ), 
        element(8918, "United Kingdom" ), 
        element(8919, "Grenada" ), 
        element(8920, "Georgia" ), 
        element(8921, "French Guiana" ), 
        element(8922, "Ghana" ), 
        element(8923, "Gibraltar" ), 
        element(8924, "Greenland" ), 
        element(8925, "Gambia" ), 
        element(8926, "Guinea" ), 
        element(8927, "Guadeloupe" ), 
        element(8928, "Equatorial Guinea" ), 
        element(8929, "Greece" ), 
        element(8930, "S. Georgia and S. Sandwich Isls." ), 
        element(8931, "Guatemala" ), 
        element(8932, "Guam" ), 
        element(8933, "Guinea-Bissau" ), 
        element(8934, "Guyana" ), 
        element(8935, "Hong Kong" ), 
        element(8936, "Heard and McDonald Islands" ), 
        element(8937, "Honduras" ), 
        element(8938, "Croatia (Hrvatska)" ), 
        element(8939, "Haiti" ), 
        element(8940, "Hungary" ), 
        element(8941, "Indonesia" ), 
        element(8942, "Ireland" ), 
        element(8943, "Israel" ), 
        element(8944, "India" ), 
        element(8945, "British Indian Ocean Territory" ), 
        element(8946, "Iraq" ), 
        element(8947, "Iran" ), 
        element(8948, "Iceland" ), 
        element(8949, "Italy" ), 
        element(8950, "Jamaica" ), 
        element(8951, "Jordan" ), 
        element(8952, "Japan" ), 
        element(8953, "Kenya" ), 
        element(8954, "Kyrgyzstan" ), 
        element(8955, "Cambodia" ), 
        element(8956, "Kiribati" ), 
        element(8957, "Comoros" ), 
        element(8958, "Saint Kitts and Nevis" ), 
        element(8959, "Korea (North)" ), 
        element(8960, "Korea (South)" ), 
        element(8961, "Kuwait" ), 
        element(8962, "Cayman Islands" ), 
        element(8963, "Kazakhstan" ), 
        element(8964, "Laos" ), 
        element(8965, "Lebanon" ), 
        element(8966, "Saint Lucia" ), 
        element(8967, "Liechtenstein" ), 
        element(8968, "Sri Lanka" ), 
        element(8969, "Liberia" ), 
        element(8970, "Lesotho" ), 
        element(8971, "Lithuania" ), 
        element(8972, "Luxembourg" ), 
        element(8973, "Latvia" ), 
        element(8974, "Libya" ), 
        element(8975, "Morocco" ), 
        element(8976, "Monaco" ), 
        element(8977, "Moldova", 
            element(9085, "Anenii Noi County", 
                element(9108, "Bulboaca", 
                    element(9118, "Bulboaca Veche" ) ), 
                element(9109, "Hulboaca" ), 
                element(9110, "Dolboaca" ) ), 
            element(9086, "Balti County", 
                element(9111, "Glodeni", 
                    element(9117, "Viisoara" ) ), 
                element(9112, "Raureni" ), 
                element(9113, "Apareni" ) ), 
            element(9087, "Cahul County", 
                element(9120, "AAA", 
                    element(9121, "some new location" ) ) ), 
            element(9088, "Chisinau City" ), 
            element(9089, "Chisinau County" ), 
            element(9090, "Drochia County" ), 
            element(9091, "Dubasari County" ), 
            element(9092, "Edinet County" ), 
            element(9093, "Falesti County" ), 
            element(9094, "Hincesti County" ), 
            element(9095, "Ialoveni County" ), 
            element(9096, "Lapusna County" ), 
            element(9097, "Leova County" ), 
            element(9098, "Nisporeni County" ), 
            element(9099, "Orhei County" ), 
            element(9100, "Riscani County" ), 
            element(9101, "Soroca County" ), 
            element(9102, "Straseni County" ), 
            element(9103, "Telenesti County" ), 
            element(9104, "Tighina County" ), 
            element(9105, "Transnistrian Region", 
                element(9114, "Tiraspol" ), 
                element(9115, "Slobozia" ), 
                element(9116, "Camenca" ) ), 
            element(9106, "U.T.A. Gagauzia" ), 
            element(9107, "Ungheni County" ) ), 
        element(8978, "Madagascar" ), 
        element(8979, "Marshall Islands" ), 
        element(8980, "Macedonia" ), 
        element(8981, "Mali" ), 
        element(8982, "Myanmar" ), 
        element(8983, "Mongolia" ), 
        element(8984, "Macau" ), 
        element(8985, "Northern Mariana Islands" ), 
        element(8986, "Martinique" ), 
        element(8987, "Mauritania" ), 
        element(8988, "Montserrat" ), 
        element(8989, "Malta" ), 
        element(8990, "Mauritius" ), 
        element(8991, "Maldives" ), 
        element(8992, "Malawi" ), 
        element(8993, "Mexico" ), 
        element(8994, "Malaysia" ), 
        element(8995, "Mozambique" ), 
        element(8996, "Namibia" ), 
        element(8997, "New Caledonia" ), 
        element(8998, "Niger" ), 
        element(8999, "Norfolk Island" ), 
        element(9000, "Nigeria" ), 
        element(9001, "Nicaragua" ), 
        element(9002, "Netherlands" ), 
        element(9003, "Norway" ), 
        element(9004, "Nepal" ), 
        element(9005, "Nauru" ), 
        element(9006, "Niue" ), 
        element(9007, "New Zealand (Aotearoa)" ), 
        element(9008, "Oman" ), 
        element(9009, "Panama" ), 
        element(9010, "Peru" ), 
        element(9011, "French Polynesia" ), 
        element(9012, "Papua New Guinea" ), 
        element(9013, "Philippines" ), 
        element(9014, "Pakistan" ), 
        element(9015, "Poland" ), 
        element(9016, "St. Pierre and Miquelon" ), 
        element(9017, "Pitcairn" ), 
        element(9018, "Puerto Rico" ), 
        element(9019, "Palestinian Territory, Occupied" ), 
        element(9020, "Portugal" ), 
        element(9021, "Palau" ), 
        element(9022, "Paraguay" ), 
        element(9023, "Qatar" ), 
        element(9024, "Reunion" ), 
        element(9025, "Romania" ), 
        element(9026, "Russian Federation" ), 
        element(9027, "Rwanda" ), 
        element(9028, "Saudi Arabia" ), 
        element(9029, "Solomon Islands" ), 
        element(9030, "Seychelles" ), 
        element(9031, "Sudan" ), 
        element(9032, "Sweden" ), 
        element(9033, "Singapore" ), 
        element(9034, "St. Helena" ), 
        element(9035, "Slovenia" ), 
        element(9036, "Svalbard and Jan Mayen Islands" ), 
        element(9037, "Slovak Republic" ), 
        element(9038, "Sierra Leone" ), 
        element(9039, "San Marino" ), 
        element(9040, "Senegal" ), 
        element(9041, "Somalia" ), 
        element(9042, "Suriname" ), 
        element(9043, "Sao Tome and Principe" ), 
        element(9044, "El Salvador" ), 
        element(9045, "Syria" ), 
        element(9046, "Swaziland" ), 
        element(9047, "Turks and Caicos Islands" ), 
        element(9048, "Chad" ), 
        element(9049, "French Southern Territories" ), 
        element(9050, "Togo" ), 
        element(9051, "Thailand" ), 
        element(9052, "Tajikistan" ), 
        element(9053, "Tokelau" ), 
        element(9054, "Turkmenistan" ), 
        element(9055, "Tunisia" ), 
        element(9056, "Tonga" ), 
        element(9057, "East Timor" ), 
        element(9058, "Turkey" ), 
        element(9059, "Trinidad and Tobago" ), 
        element(9060, "Tuvalu" ), 
        element(9061, "Taiwan" ), 
        element(9062, "Tanzania" ), 
        element(9063, "Ukraine" ), 
        element(9064, "Uganda" ), 
        element(9065, "US Minor Outlying Islands" ), 
        element(9066, "United States" ), 
        element(9067, "Uruguay" ), 
        element(9068, "Uzbekistan" ), 
        element(9069, "Vatican City State (Holy See)" ), 
        element(9070, "Saint Vincent and the Grenadines" ), 
        element(9071, "Venezuela" ), 
        element(9072, "Virgin Islands (British)" ), 
        element(9073, "Virgin Islands (U.S.)" ), 
        element(9074, "Viet Nam" ), 
        element(9075, "Vanuatu" ), 
        element(9076, "Wallis and Futuna Islands" ), 
        element(9077, "Samoa" ), 
        element(9078, "Yemen" ), 
        element(9079, "Mayotte" ), 
        element(9080, "Yugoslavia" ), 
        element(9081, "South Africa" ), 
        element(9082, "Zambia" ), 
        element(9083, "Zimbabwe" ), 
        element(9084, "Country A" ), 
        element(9119, "Multi-country" ));
    }
}

