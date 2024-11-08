require 'fileutils'

# Méthode pour convertir une chaîne en PascalCase
def camelize(str)
  str.gsub(/(?:^|_)([a-z])/) { $1.upcase }
end

# Méthode pour générer une interface TypeScript à partir de PlausibleProps
def generate_ts_interface_from_props(class_name, props, output_file_path)
  # Création des paramètres d'interface
  ts_interface_params = props.map do |key, _value|
    "#{key}?: string;"
  end

  # Génération de l'interface TypeScript
  ts_interface_name = camelize(class_name)
  ts_interface = "export interface #{ts_interface_name} {\n  #{ts_interface_params.join("\n  ")}\n}\n"

  # Écriture de l'interface dans le fichier de sortie
  File.open(output_file_path, 'a') do |file|
    file.puts ts_interface
  end

  puts "TypeScript interface generated at: #{output_file_path}"
end

# Méthode pour analyser le fichier Kotlin et extraire les propriétés de PlausibleProps
def process_kotlin_props_file(file_path, output_file_path)
  props = []

  File.readlines(file_path).each do |line|
    if line =~ /^\s*"(\w+)"\s*to\s*initialProps\["\w+"\]/
      # Récupération de la clé (le nom de la propriété)
      prop_name = line.match(/^\s*"(\w+)"/)[1]
      props << [prop_name, "string"]
    end
  end

  # Génération de l'interface TypeScript à partir des propriétés extraites
  generate_ts_interface_from_props("PlausibleProps", props, output_file_path)
end

# Définir le chemin du fichier d'entrée et du dossier de sortie
input_file = File.expand_path('../../mealz-shared-analytics/src/commonMain/kotlin/mealz/ai/PlausibleProps.kt', __dir__)
output_file_path = File.expand_path('../../mealz-shared-analytics/dist/main.d.ts', __dir__)

# Analyse du fichier Kotlin pour générer l'interface TypeScript
process_kotlin_props_file(input_file, output_file_path)
