class CreateSteps < ActiveRecord::Migration
  def change
    create_table :steps do |t|
      t.string :description
      t.references :preparation_type, index: true, foreign_key: true

      t.timestamps null: false
    end
  end
end
