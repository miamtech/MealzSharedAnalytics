require 'set'
require 'fileutils'

# Method to generate a TypeScript interface for PlausibleProps with all properties
def generate_ts_interface(properties, output_file_path)
  # Generate TypeScript properties with `string` type for each key
  ts_interface_params = properties.map { |prop| "#{prop}?: string;" }

  # Generate the TypeScript interface
  ts_interface = <<~INTERFACE
    export interface PlausibleProps {
      #{ts_interface_params.join("\n  ")}
    }
  INTERFACE

  # Write the TypeScript interface to the file
  File.open(output_file_path, 'a') do |file|
    file.puts ts_interface
  end

  puts "TypeScript interface for PlausibleProps generated at: #{output_file_path}"
end

# Method to process Kotlin files to extract all properties
def process_kotlin_file(file_path, output_file_path)
  properties = Set.new

  # Read the Kotlin file
  lines = File.readlines(file_path)

  # Flag to track when we're inside the PROPS_FOR_EVENT block
  inside_props_map = false

  lines.each do |line|
    # Detect the start of the PROPS_FOR_EVENT block
    if line.include?('PROPS_FOR_EVENT = PlatformMap')
      inside_props_map = true
      next
    end

    # Detect the end of the PROPS_FOR_EVENT block
    if inside_props_map && line.strip == ')'
      inside_props_map = false
      next
    end

    # Extract properties within propsOf(PlatformList(...), PlatformList(...))
    if inside_props_map && line =~ /PlatformList\(([^)]+)\)/
      properties_list = line.scan(/"([^"]+)"/).flatten
      properties.merge(properties_list)
    end
  end

  # Generate the TypeScript interface
  generate_ts_interface(properties.to_a, output_file_path)
end

# Define the path for the input Kotlin file and the output TypeScript file
input_file_path = File.expand_path('../../mealz-shared-analytics/src/commonMain/kotlin/mealz/ai/EventService.kt', __dir__)
output_file_path = File.expand_path('../../mealz-shared-analytics/dist/main.d.ts', __dir__)

# Process the Kotlin file to generate the TypeScript interface
process_kotlin_file(input_file_path, output_file_path)
